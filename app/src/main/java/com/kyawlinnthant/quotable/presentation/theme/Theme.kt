package com.kyawlinnthant.quotable.presentation.theme

import android.app.Activity
import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.kyawlinnthant.quotable.data.store.ThemeType

private val lightColorScheme =
    lightColorScheme(
        primary = md_theme_light_primary,
        onPrimary = md_theme_light_onPrimary,
        primaryContainer = md_theme_light_primaryContainer,
        onPrimaryContainer = md_theme_light_onPrimaryContainer,
        secondary = md_theme_light_secondary,
        onSecondary = md_theme_light_onSecondary,
        secondaryContainer = md_theme_light_secondaryContainer,
        onSecondaryContainer = md_theme_light_onSecondaryContainer,
        tertiary = md_theme_light_tertiary,
        onTertiary = md_theme_light_onTertiary,
        tertiaryContainer = md_theme_light_tertiaryContainer,
        onTertiaryContainer = md_theme_light_onTertiaryContainer,
        error = md_theme_light_error,
        errorContainer = md_theme_light_errorContainer,
        onError = md_theme_light_onError,
        onErrorContainer = md_theme_light_onErrorContainer,
        background = md_theme_light_background,
        onBackground = md_theme_light_onBackground,
        surface = md_theme_light_surface,
        onSurface = md_theme_light_onSurface,
        surfaceVariant = md_theme_light_surfaceVariant,
        onSurfaceVariant = md_theme_light_onSurfaceVariant,
        outline = md_theme_light_outline,
        inverseOnSurface = md_theme_light_inverseOnSurface,
        inverseSurface = md_theme_light_inverseSurface,
        inversePrimary = md_theme_light_inversePrimary,
    )

private val darkColorScheme =
    darkColorScheme(
        primary = md_theme_dark_primary,
        onPrimary = md_theme_dark_onPrimary,
        primaryContainer = md_theme_dark_primaryContainer,
        onPrimaryContainer = md_theme_dark_onPrimaryContainer,
        secondary = md_theme_dark_secondary,
        onSecondary = md_theme_dark_onSecondary,
        secondaryContainer = md_theme_dark_secondaryContainer,
        onSecondaryContainer = md_theme_dark_onSecondaryContainer,
        tertiary = md_theme_dark_tertiary,
        onTertiary = md_theme_dark_onTertiary,
        tertiaryContainer = md_theme_dark_tertiaryContainer,
        onTertiaryContainer = md_theme_dark_onTertiaryContainer,
        error = md_theme_dark_error,
        errorContainer = md_theme_dark_errorContainer,
        onError = md_theme_dark_onError,
        onErrorContainer = md_theme_dark_onErrorContainer,
        background = md_theme_dark_background,
        onBackground = md_theme_dark_onBackground,
        surface = md_theme_dark_surface,
        onSurface = md_theme_dark_onSurface,
        surfaceVariant = md_theme_dark_surfaceVariant,
        onSurfaceVariant = md_theme_dark_onSurfaceVariant,
        outline = md_theme_dark_outline,
        inverseOnSurface = md_theme_dark_inverseOnSurface,
        inverseSurface = md_theme_dark_inverseSurface,
        inversePrimary = md_theme_dark_inversePrimary,
    )

sealed interface AppTheme {
    data object DynamicDark : AppTheme

    data object DynamicLight : AppTheme

    data object Dark : AppTheme

    data object Light : AppTheme
}

infix fun ThemeType.asAppTheme(enabledDarkMode: Boolean): AppTheme {
    return when (this) {
        ThemeType.DARK -> AppTheme.Dark
        ThemeType.LIGHT -> AppTheme.Light
        ThemeType.SYSTEM -> if (enabledDarkMode) AppTheme.DynamicDark else AppTheme.DynamicLight
    }
}

@Composable
fun QuotableTheme(
    appTheme: AppTheme,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val isDynamicColorAvailable = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

    val quotableColor =
        when (appTheme) {
            AppTheme.Dark -> darkColorScheme
            AppTheme.Light -> lightColorScheme
            AppTheme.DynamicDark -> if (isDynamicColorAvailable) dynamicDarkColorScheme(context) else darkColorScheme
            AppTheme.DynamicLight -> if (isDynamicColorAvailable) dynamicLightColorScheme(context) else lightColorScheme
        }

    val isDarkIcon =
        when (appTheme) {
            AppTheme.Dark -> false
            AppTheme.DynamicDark -> false
            AppTheme.DynamicLight -> true
            AppTheme.Light -> true
        }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = isDarkIcon
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = isDarkIcon
        }
    }
    MaterialTheme(
        colorScheme = quotableColor,
        typography = quotableTypo,
        shapes = quotableShape,
        content = content,
    )
}
