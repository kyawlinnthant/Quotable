package com.kyawlinnthant.quotable.domain.repo

import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.domain.vo.Quote

interface QuoteRepository {
    suspend fun randomQuotes(): NetworkResult<List<Quote>>
}