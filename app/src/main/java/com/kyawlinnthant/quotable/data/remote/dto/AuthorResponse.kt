package com.kyawlinnthant.quotable.data.remote.dto

import com.kyawlinnthant.quotable.domain.vo.Author
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthorResponse(
    @SerialName("_id")
    val id: String,
    val bio: String,
    val dateAdded: String,
    val dateModified: String,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String,
    val quotes: List<QuotesResponse>? = null,
) {
    fun toDomain() =
        Author(
            id = this.id,
            bio = this.bio,
            dateAdded = this.dateAdded,
            dateModified = this.dateModified,
            link = this.link,
            name = this.name,
            quoteCount = this.quoteCount,
            slug = this.slug,
            description = this.description,
            quotes = this.quotes?.map { it.toDomain() } ?: emptyList(),
        )
}
