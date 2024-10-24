package com.kyawlinnthant.quotable.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthorsResponse(
    val count: Int,
    val lastItemIndex: Int?,
    val page: Int,
    val results: List<AuthorResponse>,
    val totalCount: Int,
    val totalPages: Int,
)
