package com.kyawlinnthant.quotable.presentation.feature.author

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.state.StateManager
import com.kyawlinnthant.quotable.presentation.state.manage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel
    @Inject
    constructor(
        private val repository: QuoteRepository,
        private val navigator: Navigator,
    ) : ViewModel(),
        StateManager<AuthorState, AuthorAction, AuthorEvent> by manage(
            initialUiState = AuthorState.INITIAL,
        ) {
        override fun onAction(uiAction: AuthorAction) {
            when (uiAction) {
                is AuthorAction.OnQuoteClicked -> navigateToQuote(uiAction.id)
            }
        }

        fun fetchAuthor(id: String) {
            viewModelScope.launch {
                updateUiState {
                    copy(uiState = RequestState.Loading)
                }
                when (val response = repository.fetchAuthorDetail(id = id)) {
                    is NetworkResult.Failed -> {
                        updateUiState { copy(uiState = RequestState.Error(message = response.error)) }
                    }

                    is NetworkResult.Success -> {
                        updateUiState { copy(uiState = RequestState.Success(data = response.data)) }
                    }
                }
            }
        }

        private fun navigateToQuote(id: String) {
            Log.d("TAG", "navigateToQuote: $id")
            viewModelScope.launch {
                navigator.popUp()
            }
        }
    }
