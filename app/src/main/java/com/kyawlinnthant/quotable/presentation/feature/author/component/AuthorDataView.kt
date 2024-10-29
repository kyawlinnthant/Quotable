package com.kyawlinnthant.quotable.presentation.feature.author.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.quotable.R
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AuthorDataView(
    modifier: Modifier = Modifier,
    author: Author,
    onQuoteClicked: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        state = rememberLazyListState(),
    ) {
        item {
            Spacer(modifier = modifier.statusBarsPadding())
        }
        stickyHeader {
            Text(
                author.name,
                style =
                    MaterialTheme.typography.displayMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ),
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.dimen.base2x),
            )
        }
        item {
            Text(
                author.bio,
                style = MaterialTheme.typography.titleMedium,
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(horizontal = MaterialTheme.dimen.base2x),
            )
        }
        item {
            Text(
                author.description,
                style =
                    MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.outline,
                    ),
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(
                            start = MaterialTheme.dimen.base2x,
                            end = MaterialTheme.dimen.base2x,
                            top = MaterialTheme.dimen.base2x,
                        ),
            )
        }
        item {
            Text(
                author.link,
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        color = MaterialTheme.colorScheme.tertiary,
                    ),
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimen.base2x),
            )
        }

        if (author.quotes.isNotEmpty()) {
            item {
                Text(
                    stringResource(R.string.quotes_count, author.quotes.size),
                    style =
                        MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.dimen.base2x),
                )
            }
            items(
                items = author.quotes,
                key = { it.id },
            ) {
                AuthorQuoteItem(quote = it, onQuoteClicked = onQuoteClicked)
            }
        }

        item {
            Spacer(modifier = modifier.navigationBarsPadding())
        }
    }
}

@Composable
@Preview
private fun AuthorDataViewPreview() {
    LightModePreview {
        AuthorDataView(
            author = Author.MOCK,
        )
    }
}
