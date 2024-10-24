package com.kyawlinnthant.quotable.presentation.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface MVI<UiState, UiAction, SideEffect> {
    val uiState: StateFlow<UiState>
    val uiEvent: Flow<SideEffect>

    fun onAction(action: UiAction) {}

    fun MVI<UiState, UiAction, SideEffect>.updateUiState(block: UiState.() -> UiState)

    fun MVI<UiState, UiAction, SideEffect>.emitSideEffect(
        effect: SideEffect,
        scope: CoroutineScope,
    )
}

class MVIDelegate<UiState, UiAction, SideEffect> internal constructor(
    initialUiState: UiState,
) : MVI<UiState, UiAction, SideEffect> {
    private val _uiState = MutableStateFlow(initialUiState)
    override val uiState: StateFlow<UiState> by lazy { _uiState.asStateFlow() }

    private val _uiEvent by lazy { Channel<SideEffect>() }
    override val uiEvent: Flow<SideEffect> by lazy { _uiEvent.receiveAsFlow() }

    override fun MVI<UiState, UiAction, SideEffect>.updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    override fun MVI<UiState, UiAction, SideEffect>.emitSideEffect(
        effect: SideEffect,
        scope: CoroutineScope,
    ) {
        scope.launch {
            _uiEvent.send(effect)
        }
    }
}

fun <UiState, UiAction, SideEffect> mvi(initialUiState: UiState): MVI<UiState, UiAction, SideEffect> =
    MVIDelegate(
        initialUiState = initialUiState,
    )
