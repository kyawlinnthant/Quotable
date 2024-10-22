package com.kyawlinnthant.quotable.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.kyawlinnthant.quotable.core.navigation.Destination
import com.kyawlinnthant.quotable.core.navigation.NavigationAction
import com.kyawlinnthant.quotable.presentation.author.AuthorScreen
import com.kyawlinnthant.quotable.presentation.author.AuthorViewModel
import com.kyawlinnthant.quotable.presentation.authors.AuthorsScreen
import com.kyawlinnthant.quotable.presentation.authors.AuthorsViewModel
import com.kyawlinnthant.quotable.presentation.mvi.CollectSideEffect
import com.kyawlinnthant.quotable.presentation.onboard.OnBoardScreen
import com.kyawlinnthant.quotable.presentation.onboard.OnBoardViewModel
import kotlinx.coroutines.flow.Flow

@Composable
fun QuotableGraph(
    startDestination: Destination,
    navigationActions: Flow<NavigationAction>
) {
    val navController = rememberNavController()
    CollectSideEffect(navigationActions) {
        when (it) {
            is NavigationAction.NavigateTo -> navController.navigate(it.destination) {
                it.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        navigation<Destination.OnBoardGraph>(
            startDestination = Destination.OnBoardScreen,
        ) {
            composable<Destination.OnBoardScreen> {
                val vm: OnBoardViewModel = hiltViewModel()
                val uiState = vm.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    vm.onInit()
                }
                OnBoardScreen(
                    uiState = uiState.value.uiState,
                    sideEffect = vm.sideEffect,
                    onAction = vm::onAction
                )
            }
        }

        navigation<Destination.AuthorGraph>(
            startDestination = Destination.AuthorsScreen,
        ) {
            composable<Destination.AuthorsScreen> {
                val vm: AuthorsViewModel = hiltViewModel()
                val uiState = vm.uiState.collectAsStateWithLifecycle()
                AuthorsScreen(
                    uiState = uiState.value.uiState,
                    sideEffect = vm.uiEvent,
                    onAction = vm::onAction
                )
            }

            composable<Destination.AuthorDetailScreen> {
                val args = it.toRoute<Destination.AuthorDetailScreen>()
                val vm: AuthorViewModel = hiltViewModel()
                val uiState = vm.uiState.collectAsStateWithLifecycle()
                LaunchedEffect(Unit) {
                    vm.onInit(id = args.id)
                }
                AuthorScreen(
                    uiState = uiState.value.uiState,
                    sideEffect = vm.sideEffect,
                    onAction = vm::onAction
                )
            }
        }
    }
}