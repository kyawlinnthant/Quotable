package com.kyawlinnthant.quotable.presentation.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.kyawlinnthant.quotable.presentation.preview.BooleanPreviewProvider
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun ThemeChooserRadio(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .selectable(
                    selected = selected,
                    role = Role.RadioButton,
                    onClick = onClick,
                )
                .padding(MaterialTheme.dimen.base),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text)
        RadioButton(
            selected = selected,
            onClick = null,
            colors =
                RadioButtonDefaults.colors(
                    selectedColor = MaterialTheme.colorScheme.primary,
                ),
        )
    }
}

@Preview
@Composable
private fun ThemeChooserRadioPreview(
    @PreviewParameter(BooleanPreviewProvider::class) enabled: Boolean,
) {
    LightModePreview {
        ThemeChooserRadio(
            text = "Light Mode",
            selected = enabled,
        ) { }
    }
}
