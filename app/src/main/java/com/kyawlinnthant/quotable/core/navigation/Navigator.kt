package com.kyawlinnthant.quotable.core.navigation

import androidx.navigation.NavOptionsBuilder
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

interface Navigator {
    val navigationActions: Flow<NavigationAction>

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun popUp()
}

class AppNavigator @Inject constructor() : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(NavigationAction.NavigateTo(destination, navOptions))
    }

    override suspend fun popUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}

