package com.wafflestudio.snugo.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.wafflestudio.snugo.LocalNavController
import com.wafflestudio.snugo.navigation.NavigationDestination

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = Color.Cyan),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "home screen",
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "도착 상세 화면으로",
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable {
                        navController.navigate(NavigationDestination.ArrivalDetail.route)
                    },
        )
    }
}
