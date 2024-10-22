package com.kyawlinnthant.quotable.presentation.authors

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.domain.vo.Author


sealed interface AuthorsUiState {
    data object Idle : AuthorsUiState
    data object Loading : AuthorsUiState
    data class Error(val message: String) : AuthorsUiState
    data class Success(val data: List<Author>) : AuthorsUiState
}

@Immutable
data class AuthorsState(
    val uiState: AuthorsUiState
) {
    companion object {
        val INITIAL = AuthorsState(
            uiState = AuthorsUiState.Idle
        )
    }
}

@Immutable
sealed interface AuthorsAction {
    data class OnFetchAuthors(val id: String) : AuthorsAction
}

@Immutable
sealed interface AuthorsEvent {
    data class OnPrompt(val message: String) : AuthorsEvent
}