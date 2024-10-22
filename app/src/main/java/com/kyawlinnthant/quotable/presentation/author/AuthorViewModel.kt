package com.kyawlinnthant.quotable.presentation.author

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.mvi.MVI
import com.kyawlinnthant.quotable.presentation.mvi.mvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val navigator: Navigator,
) : ViewModel(),
    MVI<AuthorState, AuthorAction, AuthorEvent> by mvi(
        initialUiState = AuthorState.INITIAL,
    ) {

    override fun onAction(uiAction: AuthorAction) {
        when (uiAction) {
            AuthorAction.OnBack -> onBack()
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

    private fun onBack() {
        viewModelScope.launch {
            navigator.popUp()
        }
    }

}