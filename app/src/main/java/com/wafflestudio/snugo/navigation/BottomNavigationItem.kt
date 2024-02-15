package com.wafflestudio.snugo.navigation

import com.wafflestudio.snugo.R

sealed class BottomNavigationItem(
    val destination: NavigationDestination,
    val title: Int,
    val icon: Int,
) {
    object Home : BottomNavigationItem(
        destination = NavigationDestination.Home,
        title = R.string.bottom_nav_home,
        icon = R.drawable.ic_home,
    )

    object Records : BottomNavigationItem(
        destination = NavigationDestination.Records,
        title = R.string.bottom_nav_records,
        icon = R.drawable.ic_list,
    )

    object Settings : BottomNavigationItem(
        destination = NavigationDestination.Settings,
        title = R.string.bottom_nav_settings,
        icon = R.drawable.ic_settings,
    )

    companion object {
        val items = listOf(Home, Records, Settings)
    }
}
