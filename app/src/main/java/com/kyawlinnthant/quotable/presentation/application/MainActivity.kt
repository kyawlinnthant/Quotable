package com.kyawlinnthant.quotable.presentation.application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        val splashScreen = installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val vm: MainViewModel = hiltViewModel()
            val appTheme = vm.appTheme.collectAsStateWithLifecycle()
            splashScreen.setKeepOnScreenCondition {
                appTheme.value == null
            }
            appTheme.value?.let {
                QuotableTheme(
                    appTheme = it,
                ) {
                    QuotableGraph(
                        startDestination = Destination.OnBoardGraph,
                        navigationActions = vm.navigator.navigationActions,
                    )
                }
            }
        }
    }
}
