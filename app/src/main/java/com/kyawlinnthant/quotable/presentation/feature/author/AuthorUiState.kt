package com.kyawlinnthant.quotable.presentation.feature.author

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.common.RequestState

@Immutable
data class AuthorState(
    val uiState: RequestState<Author>,
) {
    companion object {
        val INITIAL =
            AuthorState(
                uiState = RequestState.Idle,
            )
    }
}

@Immutable
sealed interface AuthorAction {
    data object OnBack : AuthorAction
}

@Immutable
sealed interface AuthorEvent {
    data class OnPrompt(val message: String) : AuthorEvent
}
