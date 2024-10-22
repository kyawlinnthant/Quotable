package com.kyawlinnthant.quotable.presentation.authors

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.common.RequestState


@Immutable
data class AuthorsState(
    val uiState: RequestState<List<Author>>
) {
    companion object {
        val INITIAL = AuthorsState(
            uiState = RequestState.Idle
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