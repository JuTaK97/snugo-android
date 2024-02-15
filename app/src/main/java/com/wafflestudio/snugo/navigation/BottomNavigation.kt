package com.wafflestudio.snugo.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigation(
    navController: NavController,
    modifier: Modifier = Modifier,
) {
    val items =
        listOf(
            BottomNavigationItem.Home,
            BottomNavigationItem.Records,
            BottomNavigationItem.Settings,
        )
    val backStackEntry by navController.currentBackStackEntryAsState()

    androidx.compose.material.BottomNavigation(
        modifier = modifier.fillMaxWidth(),
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                selected = backStackEntry?.destination?.route == item.destination.route,
                onClick = {
                    navController.navigate(item.destination.route) {
                        backStackEntry?.destination?.parent?.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = Color.Gray,
            )
        }
    }
}
