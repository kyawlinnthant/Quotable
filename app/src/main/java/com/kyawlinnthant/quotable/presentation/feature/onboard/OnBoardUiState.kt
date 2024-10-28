package com.kyawlinnthant.quotable.presentation.feature.onboard

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.domain.vo.Quote
import com.kyawlinnthant.quotable.presentation.common.RequestState

@Immutable
data class OnBoardState(
    val uiState: RequestState<List<Quote>>,
    val enabledDynamic: Boolean,
    val appTheme: ThemeType,
    val shouldShowDialog: Boolean,
) {
    companion object {
        val INITIAL =
            OnBoardState(
                uiState = RequestState.Idle,
                enabledDynamic = false,
                appTheme = ThemeType.SYSTEM,
                shouldShowDialog = false,
            )
    }
}

@Immutable
sealed interface OnBoardAction {
    data object OnClickAuthor : OnBoardAction

    data class OnChooseTheme(val theme: ThemeType) : OnBoardAction

    data class OnChooseDynamic(val enabled: Boolean) : OnBoardAction

    data class OnChangeDialogState(val enabled: Boolean) : OnBoardAction
}

@Immutable
sealed interface OnBoardEvent {
    data class OnPrompt(val message: String) : OnBoardEvent
}
