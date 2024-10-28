package com.kyawlinnthant.quotable.presentation.feature.authors

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.feature.authors.component.AuthorItem
import com.kyawlinnthant.quotable.presentation.mvi.CollectSideEffect
import com.kyawlinnthant.quotable.presentation.theme.dimen
import kotlinx.coroutines.flow.Flow

@Composable
fun AuthorsScreen2(
    modifier: Modifier = Modifier,
    authors: LazyPagingItems<Author>,
    sideEffect: Flow<AuthorsEvent>,
    onAction: (AuthorsAction) -> Unit,
) {
    val snackState: SnackbarHostState = remember { SnackbarHostState() }
    CollectSideEffect(sideEffect) {
        when (it) {
            is AuthorsEvent.OnPrompt -> snackState.showSnackbar(message = it.message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackState, modifier = Modifier.navigationBarsPadding())
        },
    ) { paddingValues ->

        authors.apply {
            LazyColumn(
                modifier =
                    modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                state = rememberLazyListState(),
                contentPadding = PaddingValues(MaterialTheme.dimen.base2x),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base),
            ) {
                items(
                    count = this@apply.itemCount,
                    key = this@apply.itemKey(key = { it.id }),
                ) { index ->
                    val currentItem = this@apply[index]
                    currentItem?.let {
                        AuthorItem(
                            author = it,
                            onItemClick = { id ->
                                onAction(AuthorsAction.OnClickAuthor(id = id))
                            },
                        )
                    }
                }

                when {
                    loadState.append is LoadState.Loading -> {
                        Log.d("TAG", "AuthorsScreen2: loading")
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    loadState.append is LoadState.Error -> {
                        Log.d("TAG", "AuthorsScreen2: error")
                        val error = this@apply.loadState.append as LoadState.Error
                        val message = error.error.localizedMessage ?: "Can't load data"
                        item {
                            Text(message)
                        }
                    }

                    loadState.append.endOfPaginationReached -> {
                        Log.d("TAG", "AuthorsScreen2: endOfPaginationReached")
                        item {
                            if (this@apply.itemCount != 0) {
                                // end item
                                HorizontalDivider(
                                    modifier =
                                        modifier
                                            .fillMaxWidth()
                                            .padding(vertical = MaterialTheme.dimen.small),
                                )
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = modifier.navigationBarsPadding())
                }
            }

            when (loadState.refresh) {
                is LoadState.Loading -> {
                    Log.d("TAG", "AuthorsScreen2: full loading")
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        // should be FULL SCREEN LOADING
                        CircularProgressIndicator()
                    }
                }

                is LoadState.Error -> {
                    Log.d("TAG", "AuthorsScreen2: full error")
                    val error = this.loadState.refresh as LoadState.Error
                    val message = error.error.localizedMessage ?: "Can't load data"
                    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        // should be FULL SCREEN ERROR
                        Text(message)
                    }
                }

                is LoadState.NotLoading -> {
                    Log.d("TAG", "AuthorsScreen2: full not loading")
                    Unit
                }
            }
            if (loadState.append.endOfPaginationReached) {
                Log.d("TAG", "AuthorsScreen2: full end")
                if (this@apply.itemCount == 0) {
                    Text("Empty")
                }
            }
        }
    }
}
