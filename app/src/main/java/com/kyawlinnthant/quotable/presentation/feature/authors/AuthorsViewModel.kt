package com.kyawlinnthant.quotable.presentation.feature.authors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.core.navigation.Navigator
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.presentation.state.StateManager
import com.kyawlinnthant.quotable.presentation.state.manage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthorsViewModel
    @Inject
    constructor(
        private val repository: QuoteRepository,
        private val navigator: Navigator,
    ) : ViewModel(),
        StateManager<AuthorsState, AuthorsAction, AuthorsEvent> by manage(
            initialUiState = AuthorsState.INITIAL,
        ) {
        init {
            fetchPaginatedAuthors()
        }

        private fun fetchPaginatedAuthors() {
            viewModelScope.launch {
                repository.fetchPaginatedAuthors().collectLatest {
                    updateUiState {
                        copy(
                            authors =
                                flow {
                                    emit(it)
                                }.cachedIn(viewModelScope),
                        )
                    }
                }
            }
        }

        private fun navigateToAuthorDetail(id: String) {
            viewModelScope.launch {
                navigator.navigate(Destination.AuthorDetailScreen(id))
            }
        }

        override fun onAction(action: AuthorsAction) {
            when (action) {
                is AuthorsAction.OnClickAuthor -> navigateToAuthorDetail(action.id)
            }
        }
    }
