package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.runtime.Stable
import com.kyawlinnthant.quotable.domain.vo.Quote

sealed interface OnBoardUiState {
    data object Idle : OnBoardUiState
    data object Loading : OnBoardUiState
    data class Error(val message: String) : OnBoardUiState
    data class Success(val data: List<Quote>) : OnBoardUiState
}

@Stable
data class OnBoardViewModelState(
    val uiState: OnBoardUiState = OnBoardUiState.Idle
)