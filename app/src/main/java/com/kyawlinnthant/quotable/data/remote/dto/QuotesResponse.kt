package com.kyawlinnthant.quotable.data.remote.dto

import com.kyawlinnthant.quotable.domain.vo.Quote
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesResponse(
    @SerialName("_id")
    val id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
) {
    companion object {
        val EMPTY = QuotesResponse(
            id = "",
            author = "",
            authorSlug = "",
            content = "",
            dateAdded = "",
            dateModified = "",
            length = 0,
            tags = emptyList()
        )
    }

    fun toVo() = Quote(
        id = this.id,
        author = this.author,
        authorSlug = this.authorSlug,
        content = this.content,
        dateAdded = this.dateAdded,
        dateModified = this.dateModified,
        length = this.length,
        tags = this.tags.toSet()
    )
}