package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.domain.vo.Quote

sealed interface OnBoardUiState {
    data object Idle : OnBoardUiState
    data object Loading : OnBoardUiState
    data class Error(val message: String) : OnBoardUiState
    data class Success(val data: List<Quote>) : OnBoardUiState
}

@Immutable
data class OnBoardState(
    val uiState: OnBoardUiState
) {
    companion object {
        val INITIAL = OnBoardState(
            uiState = OnBoardUiState.Idle
        )
        val DEFAULT = OnBoardState(
            uiState = OnBoardUiState.Loading
        )
    }
}

@Immutable
sealed interface OnBoardAction {
    data class OnClickItem(val id: String) : OnBoardAction
    data object DoSomething : OnBoardAction
}

@Immutable
sealed interface OnBoardEvent {
    data class OnPrompt(val message: String) : OnBoardEvent
}