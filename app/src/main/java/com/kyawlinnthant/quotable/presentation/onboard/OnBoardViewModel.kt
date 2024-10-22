package com.kyawlinnthant.quotable.presentation.onboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule.MainScope
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.mvi.MVI
import com.kyawlinnthant.quotable.presentation.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val navigator: Navigator,
    @MainScope private val scope: CoroutineScope
) : ViewModel(),
    MVI<OnBoardState, OnBoardAction, OnBoardEvent> by mvi(
        initialUiState = OnBoardState.INITIAL,
        scope = scope
    ) {

    override fun onAction(uiAction: OnBoardAction) {
        when (uiAction) {
            is OnBoardAction.OnClickAuthor -> onItemClick()
        }
    }

    fun onInit() {
        viewModelScope.launch {
            initActions.collect {
                fetchQuotes()
            }
        }
    }

    private fun fetchQuotes() {
        viewModelScope.launch {
            updateUiState {
                copy(uiState = RequestState.Loading)
            }
            when (val response = repository.fetchRandomQuotes()) {
                is NetworkResult.Failed -> {
                    updateUiState { copy(uiState = RequestState.Error(message = response.error)) }
                }

                is NetworkResult.Success -> {
                    updateUiState { copy(uiState = RequestState.Success(data = response.data)) }
                }
            }
        }
    }

    private fun onItemClick() {
        viewModelScope.launch {
            navigator.navigate(Destination.AuthorsScreen)
        }
    }

}
