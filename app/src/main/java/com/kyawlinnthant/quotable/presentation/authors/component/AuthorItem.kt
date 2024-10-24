package com.kyawlinnthant.quotable.presentation.authors.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.theme.QuotableTheme

@Composable
fun AuthorItem(
    modifier: Modifier = Modifier,
    author: Author,
    onItemClick: (String) -> Unit,
) {
    ListItem(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable {
                    onItemClick(author.id)
                },
        headlineContent = {
            Text(author.name, style = MaterialTheme.typography.displaySmall)
        },
    )
}

@Composable
@Preview
private fun AuthorItemPreview() {
    QuotableTheme {
        Surface {
            AuthorItem(author = Author.MOCK) {}
        }
    }
}
