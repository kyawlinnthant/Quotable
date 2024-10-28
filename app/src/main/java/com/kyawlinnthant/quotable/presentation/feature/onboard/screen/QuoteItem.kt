package com.kyawlinnthant.quotable.presentation.feature.onboard.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.quotable.R
import com.kyawlinnthant.quotable.domain.vo.Quote
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun QuoteItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    onItemClicked: () -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(MaterialTheme.dimen.base2x),
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            IconToggleButton(
                checked = false,
                enabled = true,
                onCheckedChange = {},
                colors =
                    IconButtonDefaults.iconToggleButtonColors(
                        contentColor =
                            MaterialTheme.colorScheme.onBackground.copy(
                                alpha = MaterialTheme.dimen.defaultAlpha,
                            ),
                        checkedContentColor = MaterialTheme.colorScheme.primary,
                    ),
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.disabled_favourite),
                    contentDescription = null,
                )
            }
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.base)) {
                items(
                    items = quote.tags.toList(),
                    key = { it },
                ) {
                    SuggestionChip(
                        onClick = {},
                        enabled = false,
                        label = {
                            Text(it, style = MaterialTheme.typography.labelSmall)
                        },
                    )
                }
            }
        }
        item {
            Text(quote.content, style = MaterialTheme.typography.displaySmall)
        }
        item {
            Column(
                modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.dimen.tiny),
            ) {
                Text(quote.dateAdded, style = MaterialTheme.typography.labelSmall)
                Text(quote.author, style = MaterialTheme.typography.labelLarge)
            }
        }
        item {
            Spacer(modifier = modifier.height(MaterialTheme.dimen.base3x))
        }
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.dimen.base),
            ) {
                IconButton(onClick = onItemClicked) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_format_list_bulleted_24),
                        contentDescription = null,
                    )
                }
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.round_share_24),
                        contentDescription = null,
                    )
                }
            }
        }

        item {
            Spacer(modifier = modifier.navigationBarsPadding())
        }
    }
}

@Composable
@Preview
private fun QuoteItemPreview() {
    LightModePreview {
        QuoteItem(quote = Quote.MOCK) {}
    }
}
