package com.kyawlinnthant.quotable.data.remote.repo

import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.data.remote.QuoteApi
import com.kyawlinnthant.quotable.data.remote.safeApiCall
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.domain.vo.Quote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepositoryImpl @Inject constructor(
    private val api: QuoteApi,
    @DispatcherModule.IoDispatcher private val io: CoroutineDispatcher
) : QuoteRepository {
    override suspend fun randomQuotes(): NetworkResult<List<Quote>> {
        return withContext(io) {
            when (val response = safeApiCall { api.randomQuotes(limit = 5) }) {
                is NetworkResult.Failed -> {
                    NetworkResult.Failed(error = response.error)
                }

                is NetworkResult.Success -> {
                    NetworkResult.Success(data = response.data.map { it.toVo() })
                }
            }
        }
    }
}