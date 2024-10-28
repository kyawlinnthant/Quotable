package com.kyawlinnthant.quotable.domain.repo

import androidx.paging.PagingData
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.domain.vo.Quote
import kotlinx.coroutines.flow.Flow

interface QuoteRepository {
    suspend fun fetchRandomQuotes(): NetworkResult<List<Quote>>

    suspend fun fetchAuthors(): NetworkResult<List<Author>>

    suspend fun fetchAuthorDetail(id: String): NetworkResult<Author>

    suspend fun fetchPaginatedAuthors(): Flow<PagingData<Author>>

    suspend fun saveTheme(theme: ThemeType)

    suspend fun getTheme(): Flow<ThemeType>

    suspend fun saveDynamic(enabled: Boolean)

    suspend fun getDynamic(): Flow<Boolean>
}
