package com.kyawlinnthant.quotable.presentation.feature.onboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.kyawlinnthant.quotable.R
import com.kyawlinnthant.quotable.data.store.ThemeType
import com.kyawlinnthant.quotable.domain.vo.Quote
import com.kyawlinnthant.quotable.presentation.common.RequestState
import com.kyawlinnthant.quotable.presentation.common.ResultDisplayView
import com.kyawlinnthant.quotable.presentation.common.dialog.ThemeDialog
import com.kyawlinnthant.quotable.presentation.feature.onboard.screen.OnBoardDataView
import com.kyawlinnthant.quotable.presentation.state.CollectSideEffect
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnBoardScreen(
    shouldShowDialog: Boolean,
    isDynamic: Boolean,
    appTheme: ThemeType,
    uiState: RequestState<List<Quote>>,
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
    if (shouldShowDialog) {
        ThemeDialog(
            onDismiss = {
                onAction(OnBoardAction.OnChangeDialogState(false))
            },
            themeType = appTheme,
            isEnabledDynamic = isDynamic,
            onChangeDarkThemeConfig = {
                onAction(OnBoardAction.OnChooseTheme(it))
            },
            onChangeDynamicEnabled = {
                onAction(OnBoardAction.OnChooseDynamic(it))
            },
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackState, modifier = Modifier.navigationBarsPadding())
        },
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = {
                        onAction(OnBoardAction.OnChangeDialogState(true))
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_settings_applications_24),
                            contentDescription = null,
                        )
                    }
                },
            )
        },
    ) { paddingValues ->

        Box(
            modifier =
                modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            ResultDisplayView<List<Quote>>(
                onIdle = {
                    Text("Idle", style = MaterialTheme.typography.bodyLarge)
                },
                onLoading = {
                    CircularProgressIndicator()
                },
                onSuccess = { vo ->
                    OnBoardDataView(
                        quotes = uiState.getSuccessData(),
                        onItemClicked = {
                            onAction(
                                OnBoardAction.OnClickAuthor,
                            )
                        },
                    )
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
