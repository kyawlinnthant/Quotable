package com.kyawlinnthant.quotable.presentation.onboard

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.mvi.MVI
import com.kyawlinnthant.quotable.presentation.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val repository: QuoteRepository,
) : ViewModel(),
    MVI<OnBoardState, OnBoardAction, OnBoardEvent> by mvi(
        initialUiState = OnBoardState.INITIAL,
        initialUiAction = OnBoardAction.DoSomething
    ) {

    override fun onAction(uiAction: OnBoardAction) {
        Log.d("MVIDelegate", "onAction block of vm")
        when (uiAction) {
            is OnBoardAction.OnClickItem -> Unit
            OnBoardAction.DoSomething -> fetchQuotes()
        }
    }
    init {
        fetchQuotes()
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
}
