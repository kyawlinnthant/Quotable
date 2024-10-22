package com.kyawlinnthant.quotable.core.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    @Serializable
    data object OnBoardGraph : Destination

    @Serializable
    data object AuthorGraph : Destination

    @Serializable
    data object OnBoardScreen : Destination

    @Serializable
    data object AuthorsScreen : Destination

    @Serializable
    data class AuthorDetailScreen(val id: String) : Destination
}