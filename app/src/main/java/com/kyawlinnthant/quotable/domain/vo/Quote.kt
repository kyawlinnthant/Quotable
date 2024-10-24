package com.kyawlinnthant.quotable.domain.vo

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class Quote(
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: Set<String>,
) {
    companion object {
        val EMPTY =
            Quote(
                id = "",
                author = "",
                authorSlug = "",
                content = "",
                dateAdded = "",
                dateModified = "",
                length = 0,
                tags = emptySet(),
            )

        val MOCK =
            Quote(
                id = UUID.randomUUID().toString(),
                author = "Confucius",
                authorSlug = "",
                content = "Fine words and an insinuating appearance are seldom associated with true virtue",
                dateAdded = "2019-03-17",
                dateModified = "2023-04-14",
                length = 79,
                tags = setOf("Wisdom", "Change", "Life"),
            )
    }
}
