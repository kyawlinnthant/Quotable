package com.kyawlinnthant.quotable.presentation.mvi

import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule.MainScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


interface MVI<UiState, UiAction, SideEffect> {
    val uiState: StateFlow<UiState>
    val sideEffect: Flow<SideEffect>
    val initActions: Flow<UiAction>
    fun onAction(uiAction: UiAction) {}
    fun MVI<UiState, UiAction, SideEffect>.updateUiState(block: UiState.() -> UiState)
    fun MVI<UiState, UiAction, SideEffect>.emitSideEffect(effect: SideEffect)
}

class MVIDelegate<UiState, UiAction, SideEffect> @Inject internal constructor(
    initialUiState: UiState,
    initialUiAction: UiAction,
    @MainScope private val scope: CoroutineScope
) : MVI<UiState, UiAction, SideEffect> {
    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState> = _uiState
        .onStart {
            scope.launch {
                _initActions.send(initialUiAction)
            }
        }
        .stateIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = initialUiState
        )

    private val _sideEffect by lazy { Channel<SideEffect>() }
    override val sideEffect: Flow<SideEffect> by lazy { _sideEffect.receiveAsFlow() }
    private val _initActions by lazy { Channel<UiAction>() }
    override val initActions: Flow<UiAction> by lazy { _initActions.receiveAsFlow() }

    override fun MVI<UiState, UiAction, SideEffect>.updateUiState(
        block: UiState.() -> UiState
    ) {
        _uiState.update(block)
    }

    override fun MVI<UiState, UiAction, SideEffect>.emitSideEffect(
        effect: SideEffect
    ) {
        scope.launch {
            _sideEffect.send(effect)
        }
    }
}

fun <UiState, UiAction, SideEffect> mvi(
    initialUiState: UiState,
    initialUiAction: UiAction,
    scope: CoroutineScope
): MVI<UiState, UiAction, SideEffect> = MVIDelegate(
    initialUiState = initialUiState,
    initialUiAction = initialUiAction,
    scope = scope
)

interface InitialUiAction