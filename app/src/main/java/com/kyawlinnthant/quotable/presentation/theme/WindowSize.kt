package com.kyawlinnthant.quotable.presentation.theme


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

private const val COMPACT_WIDTH = 600
private const val COMPACT_HEIGHT = 480
private const val MEDIUM_WIDTH = 840
private const val MEDIUM_HEIGHT = 900

data class WindowSize(
    val width: WindowType,
    val height: WindowType,
)

enum class WindowType { Compact, Medium, Expanded }

@Composable
fun rememberWindowSize(): WindowSize {
    val configuration = LocalConfiguration.current
    val screenWidth by remember(key1 = configuration) {
        mutableIntStateOf(configuration.screenWidthDp)
    }
    val screenHeight by remember(key1 = configuration) {
        mutableIntStateOf(configuration.screenHeightDp)
    }

    return WindowSize(
        width = getScreenWidth(screenWidth),
        height = getScreenHeight(screenHeight),
    )
}

fun getScreenWidth(width: Int): WindowType =
    when {
        width < COMPACT_WIDTH -> WindowType.Compact
        width < MEDIUM_WIDTH -> WindowType.Medium
        else -> WindowType.Expanded
    }

fun getScreenHeight(height: Int): WindowType =
    when {
        height < COMPACT_HEIGHT -> WindowType.Compact
        height < MEDIUM_HEIGHT -> WindowType.Medium
        else -> WindowType.Expanded
    }