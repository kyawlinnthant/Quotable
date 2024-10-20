package com.kyawlinnthant.quotable.presentation.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyawlinnthant.quotable.presentation.onboard.OnBoardScreen
import com.kyawlinnthant.quotable.presentation.onboard.OnBoardViewModel
import com.kyawlinnthant.quotable.presentation.theme.QuotableTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: OnBoardViewModel = hiltViewModel()
            val uiState = vm.uiState.collectAsStateWithLifecycle()
            LaunchedEffect(Unit) {
                vm.onInit()
            }
            QuotableTheme {
                OnBoardScreen(
                    uiState = uiState.value.uiState,
                    sideEffect = vm.sideEffect,
                    onAction = vm::onAction
                )
            }
        }
    }
}
