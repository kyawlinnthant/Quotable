package com.kyawlinnthant.quotable.data.remote

import com.kyawlinnthant.quotable.data.remote.dto.AuthorResponse
import com.kyawlinnthant.quotable.data.remote.dto.AuthorsResponse
import com.kyawlinnthant.quotable.data.remote.dto.QuotesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface QuoteApi {
    companion object {
        private const val QUOTE = "quotes/"
        const val RANDOM = "${QUOTE}random"
        const val AUTHORS = "authors"
        const val AUTHORS_DETAIL = "$AUTHORS/{id}"
    }

    @GET(RANDOM)
    suspend fun randomQuotes(
        // default: 1,min: 1,max: 50
        @Query("limit") limit: Int,
        @Query("maxLength") max: Int? = null,
        @Query("minLength") min: Int? = null,
        @Query("author") author: String? = null,
        @Query("tags") tags: String? = null,
    ): Response<List<QuotesResponse>>

    @GET(AUTHORS)
    suspend fun fetchAuthors(
        // Min: 1   Max: 150   Default: 20
        @Query("limit") limit: Int,
        // Min: 1   Default: 1
        @Query("page") page: Int,
        @Query("slug") slug: String? = null,
        // Default: "name"   values: "dateAdded", "dateModified", "name", "quoteCount"
        @Query("sortBy") sortBy: String? = null,
        // values: "asc", "desc"
        @Query("order") order: String? = null,
    ): Response<AuthorsResponse>

    @GET(AUTHORS_DETAIL)
    suspend fun fetchAuthor(
        @Path("id") id: String,
    ): Response<AuthorResponse>
}
