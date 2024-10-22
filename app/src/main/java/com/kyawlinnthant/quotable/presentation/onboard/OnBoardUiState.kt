package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.runtime.Immutable
import com.kyawlinnthant.quotable.domain.vo.Quote
import com.kyawlinnthant.quotable.presentation.common.RequestState

@Immutable
data class OnBoardState(
    val uiState: RequestState<List<Quote>>
) {
    companion object {
        val INITIAL = OnBoardState(
            uiState = RequestState.Idle
        )
    }
}

@Immutable
sealed interface OnBoardAction {
    data object OnClickAuthor : OnBoardAction
}

@Immutable
sealed interface OnBoardEvent {
    data class OnPrompt(val message: String) : OnBoardEvent
}