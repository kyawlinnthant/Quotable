package com.kyawlinnthant.quotable.presentation.feature.author.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.quotable.domain.vo.Quote
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun AuthorQuoteItem(
    modifier: Modifier = Modifier,
    quote: Quote,
    onQuoteClicked: (String) -> Unit = {},
) {
    ListItem(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable { onQuoteClicked(quote.id) },
        headlineContent = {
            Text(quote.content, style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            Row(
                modifier =
                    modifier
                        .fillMaxWidth()
                        .padding(vertical = MaterialTheme.dimen.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    modifier =
                        modifier
                            .fillMaxWidth()
                            .weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.tiny),
                ) {
                    quote.tags.forEach {
                        TagItem(value = it)
                    }
                }
                Text(quote.dateAdded, style = MaterialTheme.typography.labelSmall)
            }
        },
    )
}

@Composable
private fun TagItem(
    modifier: Modifier = Modifier,
    value: String,
) {
    Box(
        modifier =
            modifier
                .clip(MaterialTheme.shapes.small)
                .border(
                    border =
                        BorderStroke(
                            width = MaterialTheme.dimen.one,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = MaterialTheme.dimen.defaultAlpha),
                        ),
                    shape = MaterialTheme.shapes.small,
                )
                .padding(horizontal = MaterialTheme.dimen.small, vertical = MaterialTheme.dimen.tiny),
        contentAlignment = Alignment.Center,
    ) {
        Text(value, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
@Preview
private fun TagItemPreview() {
    LightModePreview {
        TagItem(
            value = "Wisdom",
        )
    }
}

@Composable
@Preview
private fun AuthorQuoteItemPreview() {
    LightModePreview {
        AuthorQuoteItem(
            quote = Quote.MOCK,
        )
    }
}
