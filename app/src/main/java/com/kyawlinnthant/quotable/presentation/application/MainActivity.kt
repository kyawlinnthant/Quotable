package com.kyawlinnthant.quotable.presentation.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.presentation.navigation.QuotableGraph
import com.kyawlinnthant.quotable.presentation.theme.QuotableTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val vm: MainViewModel = hiltViewModel()
            val appTheme = vm.appTheme.collectAsStateWithLifecycle()
            QuotableTheme(
                appTheme = appTheme.value,
            ) {
                QuotableGraph(
                    startDestination = Destination.OnBoardGraph,
                    navigationActions = vm.navigator.navigationActions,
                )
            }
        }
    }
}
