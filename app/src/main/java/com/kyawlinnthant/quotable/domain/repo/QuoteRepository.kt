package com.kyawlinnthant.quotable.domain.repo

import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.domain.vo.Quote

interface QuoteRepository {
    suspend fun fetchRandomQuotes(): NetworkResult<List<Quote>>
    suspend fun fetchAuthors(): NetworkResult<List<Author>>
    suspend fun fetchAuthorDetail(id: String): NetworkResult<Author>
}