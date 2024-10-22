package com.kyawlinnthant.quotable.presentation.authors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

@HiltViewModel
class AuthorsViewModel @Inject constructor(
    private val repository: QuoteRepository,
    private val navigator: Navigator
) : ViewModel() {

    private val vmEvent by lazy { Channel<AuthorsEvent>() }
    val uiEvent: Flow<AuthorsEvent> by lazy { vmEvent.receiveAsFlow() }


    private val vmUiState: MutableStateFlow<AuthorsState> = MutableStateFlow(AuthorsState.INITIAL)
    val uiState: StateFlow<AuthorsState> = vmUiState
        .onStart {
            fetchAuthors()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = AuthorsState.INITIAL
        )

    private fun fetchAuthors() {
        vmUiState.update {
            it.copy(
                uiState = AuthorsUiState.Loading
            )
        }
        viewModelScope.launch {
            when (val response = repository.fetchAuthors()) {
                is NetworkResult.Failed -> {
                    vmEvent.send(AuthorsEvent.OnPrompt(message = response.error))
                }

                is NetworkResult.Success -> {
                    vmUiState.update {
                        it.copy(uiState = AuthorsUiState.Success(data = response.data))
                    }
                }
            }
        }
    }

    private fun navigateToAuthorDetail(id: String) {
        viewModelScope.launch {
            navigator.navigate(Destination.AuthorDetailScreen(id))
        }
    }

    fun onAction(action: AuthorsAction) {
        when (action) {
            is AuthorsAction.OnFetchAuthors -> navigateToAuthorDetail(action.id)
        }
    }
}