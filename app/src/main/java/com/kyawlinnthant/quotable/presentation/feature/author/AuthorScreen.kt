package com.kyawlinnthant.quotable.presentation.feature.author

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.common.ResultDisplayView
import com.kyawlinnthant.quotable.presentation.feature.author.component.AuthorDataView
import com.kyawlinnthant.quotable.presentation.state.CollectSideEffect
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorScreen(
    uiState: RequestState<Author>,
    modifier: Modifier = Modifier,
    sideEffect: Flow<AuthorEvent>,
    onAction: (AuthorAction) -> Unit,
) {
    val snackState: SnackbarHostState = remember { SnackbarHostState() }
    CollectSideEffect(sideEffect) {
        when (it) {
            is AuthorEvent.OnPrompt -> snackState.showSnackbar(message = it.message)
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackState, modifier = Modifier.navigationBarsPadding())
        },
    ) { paddingValues ->

        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            ResultDisplayView<Author>(
                onIdle = {
                    Text("Idle", style = MaterialTheme.typography.bodyLarge)
                },
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = { vo ->
                    AuthorDataView(author = vo, onQuoteClicked = { onAction(AuthorAction.OnQuoteClicked(it)) })
                },
                onError = {
                    Text(
                        uiState.getErrorMessage(),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                },
                requestState = uiState,
            )
        }
    }
}
