package com.kyawlinnthant.quotable.domain.vo

import androidx.compose.runtime.Immutable

@Immutable
data class Quote(
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: Set<String>
) {
    companion object {
        val EMPTY = Quote(
            id = "",
            author = "",
            authorSlug = "",
            content = "",
            dateAdded = "",
            dateModified = "",
            length = 0,
            tags = emptySet()
        )
    }
}
