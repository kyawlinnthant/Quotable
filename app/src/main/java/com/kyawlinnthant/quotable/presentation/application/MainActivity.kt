package com.kyawlinnthant.quotable.presentation.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyawlinnthant.quotable.presentation.onboard.OnBoardAction
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
//            LaunchedEffect(Unit){
//                vm.onAction(OnBoardAction.DoSomething)
//            }
            QuotableTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    OnBoardScreen(
                        uiState = uiState.value.uiState,
                        paddingValues = innerPadding,
                        sideEffect = vm.sideEffect,
                        onAction = vm::onAction
                    )
                }
            }
        }
    }
}
