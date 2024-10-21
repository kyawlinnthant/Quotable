package com.kyawlinnthant.quotable.presentation.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule.MainScope
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.mvi.InitialUiAction
import com.kyawlinnthant.quotable.presentation.mvi.MVI
import com.kyawlinnthant.quotable.presentation.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val repository: QuoteRepository,
    @MainScope private val scope: CoroutineScope
) : ViewModel(),
    MVI<OnBoardState, OnBoardAction, OnBoardEvent> by mvi(
        initialUiState = OnBoardState.INITIAL,
        initialUiAction = OnBoardAction.OnFetchQuotes,
        scope = scope
    ) {

    override fun onAction(uiAction: OnBoardAction) {
        when (uiAction) {
            is OnBoardAction.OnClickItem -> onItemClick(uiAction.id)
            OnBoardAction.OnFetchQuotes -> fetchQuotes()
        }
    }

    fun onInit() {
        viewModelScope.launch {
            initActions.collect {
                when (it as InitialUiAction) {
                    OnBoardAction.OnFetchQuotes -> fetchQuotes()
                }
            }
        }
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            updateUiState {
                copy(uiState = OnBoardUiState.Loading)
            }
            when (val response = repository.randomQuotes()) {
                is NetworkResult.Failed -> {
                    updateUiState { copy(uiState = OnBoardUiState.Error(message = response.error)) }
                }

                is NetworkResult.Success -> {
                    updateUiState { copy(uiState = OnBoardUiState.Success(data = response.data)) }
                }
            }
        }
    }

    private fun onItemClick(id: String) {
        viewModelScope.launch {
            emitSideEffect(effect = OnBoardEvent.OnPrompt(message = id))
        }
    }

}
