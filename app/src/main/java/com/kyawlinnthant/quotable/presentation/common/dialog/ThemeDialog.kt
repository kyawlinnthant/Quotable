package com.kyawlinnthant.quotable.presentation.common.dialog

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.kyawlinnthant.quotable.R
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.presentation.preview.BooleanPreviewProvider
import com.kyawlinnthant.quotable.presentation.preview.LightModePreview
import com.kyawlinnthant.quotable.presentation.theme.dimen

@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    isAboveS: Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S,
    themeType: ThemeType,
    isEnabledDynamic: Boolean,
    onDismiss: () -> Unit,
    onChangeDarkThemeConfig: (darkThemeConfig: ThemeType) -> Unit,
    onChangeDynamicEnabled: (isEnabled: Boolean) -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(R.string.app_appearance),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            LazyColumn {
                item {
                    HorizontalDivider()
                }
                item {
                    Column(modifier.selectableGroup()) {
                        Text(
                            text = stringResource(R.string.dark_mode_preference),
                            style = MaterialTheme.typography.titleMedium,
                            modifier =
                                modifier.padding(
                                    top = MaterialTheme.dimen.base2x,
                                    bottom = MaterialTheme.dimen.base,
                                ),
                        )
                        ThemeChooserRadio(
                            text = stringResource(R.string.mode_default),
                            selected = themeType == ThemeType.SYSTEM,
                            onClick = { onChangeDarkThemeConfig(ThemeType.SYSTEM) },
                        )
                        ThemeChooserRadio(
                            text = stringResource(R.string.mode_light),
                            selected = themeType == ThemeType.LIGHT,
                            onClick = { onChangeDarkThemeConfig(ThemeType.LIGHT) },
                        )
                        ThemeChooserRadio(
                            text = stringResource(R.string.mode_dark),
                            selected = themeType == ThemeType.DARK,
                            onClick = { onChangeDarkThemeConfig(ThemeType.DARK) },
                        )
                    }
                }

                if (isAboveS) {
                    item {
                        Spacer(modifier = modifier.height(MaterialTheme.dimen.base))
                    }
                    item {
                        HorizontalDivider()
                    }

                    item {
                        Column(modifier.selectableGroup()) {
                            Text(
                                text = stringResource(R.string.dynamic_preference),
                                style = MaterialTheme.typography.titleMedium,
                                modifier =
                                    modifier.padding(
                                        top = MaterialTheme.dimen.base2x,
                                        bottom = MaterialTheme.dimen.base,
                                    ),
                            )
                            DynamicColorSwitch(
                                text = stringResource(R.string.dynamic_enabled),
                                selected = isEnabledDynamic,
                                onChecked = onChangeDynamicEnabled,
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.confirm),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary,
                )
            }
        },
    )
}

@Preview
@Composable
private fun ThemeDialogPreview(
    @PreviewParameter(BooleanPreviewProvider::class) enabled: Boolean,
) {
    LightModePreview {
        ThemeDialog(
            themeType = ThemeType.SYSTEM,
            isEnabledDynamic = enabled,
            onDismiss = {},
            onChangeDarkThemeConfig = {},
            onChangeDynamicEnabled = {},
        )
    }
}
