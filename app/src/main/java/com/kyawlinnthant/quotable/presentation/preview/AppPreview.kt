package com.kyawlinnthant.quotable.presentation.preview

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.kyawlinnthant.quotable.presentation.theme.AppTheme
import com.kyawlinnthant.quotable.presentation.theme.QuotableTheme

@Composable
fun LightModePreview(content: @Composable () -> Unit) {
    QuotableTheme(
        appTheme = AppTheme.Light,
    ) {
        Surface {
            content()
        }
    }
}

@Composable
fun DynamicLightModePreview(content: @Composable () -> Unit) {
    QuotableTheme(
        appTheme = AppTheme.DynamicLight,
    ) {
        Surface {
            content()
        }
    }
}

@Composable
fun DarkModePreview(content: @Composable () -> Unit) {
    QuotableTheme(
        appTheme = AppTheme.Dark,
    ) {
        Surface {
            content()
        }
    }
}

@Composable
fun DynamicDarkModePreview(content: @Composable () -> Unit) {
    QuotableTheme(
        appTheme = AppTheme.DynamicDark,
    ) {
        Surface {
            content()
        }
    }
}
