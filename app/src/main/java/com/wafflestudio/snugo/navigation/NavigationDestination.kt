package com.wafflestudio.snugo.navigation

import androidx.navigation.NamedNavArgument

sealed class NavigationDestination(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    object Home : NavigationDestination("home")

    object ArrivalDetail : NavigationDestination(route = "arrival_detail")

    object Records : NavigationDestination("record")

    object Settings : NavigationDestination("settings")
}
