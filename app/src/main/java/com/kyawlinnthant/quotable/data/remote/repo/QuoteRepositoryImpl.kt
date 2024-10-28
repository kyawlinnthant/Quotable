package com.kyawlinnthant.quotable.data.remote.repo

import androidx.paging.InvalidatingPagingSourceFactory
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.kyawlinnthant.quotable.core.dispatcher.DispatcherModule
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.data.remote.QuoteApi
import com.kyawlinnthant.quotable.data.remote.safeApiCall
import com.kyawlinnthant.quotable.data.store.ThemeDataSource
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.domain.repo.QuoteRepository
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.domain.vo.Quote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class QuoteRepositoryImpl
    @Inject
    constructor(
        private val api: QuoteApi,
        private val dataSource: ThemeDataSource,
        @DispatcherModule.IoDispatcher private val io: CoroutineDispatcher,
    ) : QuoteRepository {
        override suspend fun fetchRandomQuotes(): NetworkResult<List<Quote>> {
            return withContext(io) {
                when (val response = safeApiCall { api.randomQuotes(limit = 20) }) {
                    is NetworkResult.Failed -> {
                        NetworkResult.Failed(error = response.error)
                    }

                    is NetworkResult.Success -> {
                        NetworkResult.Success(data = response.data.map { it.toDomain() })
                    }
                }
            }
        }

        override suspend fun fetchAuthors(): NetworkResult<List<Author>> {
            return withContext(io) {
                when (val response = safeApiCall { api.fetchAuthors(limit = 20, page = 1) }) {
                    is NetworkResult.Failed -> {
                        NetworkResult.Failed(error = response.error)
                    }

                    is NetworkResult.Success -> {
                        NetworkResult.Success(data = response.data.results.map { it.toDomain() })
                    }
                }
            }
        }

        override suspend fun fetchAuthorDetail(id: String): NetworkResult<Author> {
            return withContext(io) {
                when (val response = safeApiCall { api.fetchAuthor(id = id) }) {
                    is NetworkResult.Failed -> {
                        NetworkResult.Failed(error = response.error)
                    }

                    is NetworkResult.Success -> {
                        NetworkResult.Success(data = response.data.toDomain())
                    }
                }
            }
        }

        override suspend fun fetchPaginatedAuthors(): Flow<PagingData<Author>> {
            val config =
                PagingConfig(
                    pageSize = 20,
                    initialLoadSize = 20,
                    prefetchDistance = 1,
                    enablePlaceholders = true,
                )
            val pagingSourceFactory =
                InvalidatingPagingSourceFactory {
                    AuthorPagingSource(
                        api = api,
                        initialPage = 1,
                    )
                }

            return Pager(
                config = config,
                pagingSourceFactory = pagingSourceFactory,
            ).flow.map { dto ->
                dto.map {
                    it.toDomain()
                }
            }.flowOn(io)
        }

        override suspend fun saveTheme(theme: ThemeType) {
            withContext(io) {
                dataSource.saveTheme(theme)
            }
        }

        override suspend fun getTheme(): Flow<ThemeType> {
            return dataSource.retrieveTheme().flowOn(io)
        }

        override suspend fun saveDynamic(enabled: Boolean) {
            withContext(io) {
                dataSource.saveDynamic(enabled)
            }
        }

        override suspend fun getDynamic(): Flow<Boolean> {
            return dataSource.retrieveDynamic().flowOn(io)
        }
    }
