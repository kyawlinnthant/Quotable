package com.kyawlinnthant.quotable.data.remote

import com.kyawlinnthant.quotable.data.remote.dto.QuotesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteApi {

    companion object {
        private const val QUOTE = "quotes/"
        const val RANDOM = "${QUOTE}random"
    }

    @GET(RANDOM)
    suspend fun randomQuotes(
        @Query("limit") limit: Int, // default: 1,min: 1,max: 50
        @Query("maxLength") max: Int? = null,
        @Query("minLength") min: Int? = null,
        @Query("author") author: String? = null,
        @Query("tags") tags: String? = null,
    ): Response<List<QuotesResponse>>
}
