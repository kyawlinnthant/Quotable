package com.kyawlinnthant.quotable.presentation.feature.authors

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.kyawlinnthant.quotable.domain.vo.Author
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.state.CollectSideEffect
import com.kyawlinnthant.quotable.presentation.theme.dimen
import kotlinx.coroutines.flow.Flow

@Composable
fun AuthorsScreen(
    modifier: Modifier = Modifier,
    uiState: RequestState<List<Author>>,
    sideEffect: Flow<AuthorsEvent>,
    onAction: (AuthorsAction) -> Unit,
) {
    val snackState: SnackbarHostState = remember { SnackbarHostState() }
    CollectSideEffect(sideEffect) {
        when (it) {
            is AuthorsEvent.OnPrompt -> snackState.showSnackbar(message = it.message)
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
            when (uiState) {
                is RequestState.Error ->
                    Text(
                        uiState.message,
                        style = MaterialTheme.typography.bodyLarge,
                    )

                RequestState.Idle -> Text("Idle", style = MaterialTheme.typography.bodyLarge)
                RequestState.Loading -> CircularProgressIndicator()
                is RequestState.Success ->
                    LazyColumn {
                        items(
                            items = uiState.data,
                            key = { it.id },
                        ) { author ->
                            Text(
                                modifier =
                                    modifier
                                        .fillMaxWidth()
                                        .padding(MaterialTheme.dimen.base)
                                        .clickable {
                                            onAction(AuthorsAction.OnClickAuthor(author.id))
                                        },
                                text = author.name,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
            }
        }
    }
}
