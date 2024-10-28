package com.kyawlinnthant.quotable.presentation.feature.authors

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.kyawlinnthant.quotable.domain.vo.Author
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class AuthorsState(
    val authors: Flow<PagingData<Author>>,
) {
    companion object {
        val INITIAL =
            AuthorsState(
                authors = emptyFlow(),
            )
    }
}

@Immutable
sealed interface AuthorsAction {
    data class OnClickAuthor(val id: String) : AuthorsAction
}

@Immutable
sealed interface AuthorsEvent {
    data class OnPrompt(val message: String) : AuthorsEvent
}
