package com.kyawlinnthant.quotable.presentation.onboard.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.kyawlinnthant.quotable.presentation.preview.BooleanPreviewProvider
import com.kyawlinnthant.quotable.presentation.theme.QuotableTheme
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun Indicator(
    modifier: Modifier = Modifier,
    iterated: Boolean,
) {
    val color =
        if (iterated) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.onBackground.copy(MaterialTheme.dimen.defaultAlpha)
        }

    Box(
        modifier =
            modifier
                .padding(MaterialTheme.dimen.small)
                .clip(CircleShape)
                .background(color)
                .size(MaterialTheme.dimen.base),
    )
}

@Composable
@Preview
private fun IndicatorPreview(
    @PreviewParameter(BooleanPreviewProvider::class) enabled: Boolean,
) {
    QuotableTheme {
        Surface {
            Indicator(iterated = enabled)
        }
    }
}
