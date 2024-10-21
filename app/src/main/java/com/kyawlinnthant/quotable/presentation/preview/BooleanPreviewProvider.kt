package com.kyawlinnthant.quotable.presentation.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BooleanPreviewProvider : PreviewParameterProvider<Boolean> {
    override val values: Sequence<Boolean>
        get() =
            sequenceOf(
                true,
                false,
            )
}