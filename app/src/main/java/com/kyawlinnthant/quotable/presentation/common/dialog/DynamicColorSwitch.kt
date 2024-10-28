package com.kyawlinnthant.quotable.presentation.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.kyawlinnthant.quotable.presentation.preview.BooleanPreviewProvider
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun DynamicColorSwitch(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onChecked: (Boolean) -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(MaterialTheme.dimen.base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text)
        Switch(
            modifier = modifier.padding(end = MaterialTheme.dimen.base),
            checked = selected,
            onCheckedChange = onChecked,
        )
    }
}

@Preview
@Composable
private fun DynamicColorSwitchPreview(
    @PreviewParameter(BooleanPreviewProvider::class) enabled: Boolean,
) {
    LightModePreview {
        DynamicColorSwitch(
            text = "Enabled Dynamic Color",
            selected = enabled,
        ) { }
    }
}
