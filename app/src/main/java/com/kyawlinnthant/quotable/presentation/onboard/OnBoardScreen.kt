package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawlinnthant.quotable.presentation.mvi.CollectSideEffect
import kotlinx.coroutines.flow.Flow

@Composable
fun OnBoardScreen(
    uiState: OnBoardUiState,
    paddingValues: PaddingValues,
    modifier: Modifier = Modifier,
    sideEffect: Flow<OnBoardEvent>,
    onAction: (OnBoardAction) -> Unit,
) {

    CollectSideEffect(sideEffect) { when (it) {
        is OnBoardEvent.OnPrompt -> Unit
    } }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues), contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is OnBoardUiState.Error -> Text(
                uiState.message,
                style = MaterialTheme.typography.bodyLarge
            )

            OnBoardUiState.Idle -> Text("Idle", style = MaterialTheme.typography.bodyLarge)
            OnBoardUiState.Loading -> CircularProgressIndicator()
            is OnBoardUiState.Success -> LazyColumn {
                items(items = uiState.data, key = { it.id }) {
                    Text(it.content, style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}