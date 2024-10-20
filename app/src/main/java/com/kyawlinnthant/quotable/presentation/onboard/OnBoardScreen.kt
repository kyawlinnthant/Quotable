package com.kyawlinnthant.quotable.presentation.onboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItem
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
                is OnBoardUiState.Success -> LazyColumn(modifier = modifier.fillMaxSize()) {
                    items(items = uiState.data, key = { it.id }) {
                        ListItem(
                            modifier = modifier.clickable {
                                onAction(OnBoardAction.OnClickItem(it.id))
                            },
                            headlineContent = {
                                Text(it.content, style = MaterialTheme.typography.bodyLarge)
                            },
                            overlineContent = {
                                Text(it.author, style = MaterialTheme.typography.bodyLarge)
                            },
                            supportingContent = {
                                Text(it.tags.toString(), style = MaterialTheme.typography.bodyLarge)
                            },
                            leadingContent = {
                                Text(it.dateAdded, style = MaterialTheme.typography.bodyLarge)
                            },
                            trailingContent = {
                                Text(it.dateModified, style = MaterialTheme.typography.bodyLarge)
                            },
                        )
                    }
                }
            }
        }
    }


}