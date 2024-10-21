package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawlinnthant.quotable.presentation.mvi.CollectSideEffect
import com.kyawlinnthant.quotable.presentation.onboard.screen.OnBoardDataView
import kotlinx.coroutines.flow.Flow

@Composable
fun OnBoardScreen(
    uiState: OnBoardUiState,
    modifier: Modifier = Modifier,
    sideEffect: Flow<OnBoardEvent>,
    onAction: (OnBoardAction) -> Unit,
) {

    val snackState: SnackbarHostState = remember { SnackbarHostState() }
    CollectSideEffect(sideEffect) {
        when (it) {
            is OnBoardEvent.OnPrompt -> snackState.showSnackbar(message = it.message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackState, modifier = Modifier.navigationBarsPadding())
        },
    ) { paddingValues ->
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
                is OnBoardUiState.Success -> OnBoardDataView(quotes = uiState.data)
            }
        }
    }


}