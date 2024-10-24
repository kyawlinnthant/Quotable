package com.kyawlinnthant.quotable.data.remote.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams
import androidx.paging.PagingSource.LoadResult
import androidx.paging.PagingSource.LoadResult.Error
import androidx.paging.PagingState
import com.kyawlinnthant.quotable.data.remote.NetworkResult
import com.kyawlinnthant.quotable.data.remote.QuoteApi
import com.kyawlinnthant.quotable.data.remote.dto.AuthorResponse
import com.kyawlinnthant.quotable.data.remote.safeApiCall
import javax.inject.Inject

class AuthorPagingSource
    @Inject
    constructor(
        private val api: QuoteApi,
        private val initialPage: Int,
    ) : PagingSource<Int, AuthorResponse>() {
        override fun getRefreshKey(state: PagingState<Int, AuthorResponse>): Int? {
            return state.anchorPosition?.let { anchorPosition ->
                state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
            }
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthorResponse> {
            val currentPage = params.key ?: initialPage

            return when (
                val response =
                    safeApiCall {
                        api.fetchAuthors(
                            limit = params.loadSize,
                            page = currentPage,
                        )
                    }
            ) {
                is NetworkResult.Failed -> {
                    Error(Throwable(message = response.error))
                }

                is NetworkResult.Success -> {
                    val authors = response.data.results
                    val nextKey = if (authors.isEmpty()) null else currentPage + 1
                    Log.d("TAG", "page: $currentPage")

                    LoadResult.Page(
                        data = authors,
                        prevKey = null,
                        nextKey = nextKey,
                    )
                }
            }
        }
    }
