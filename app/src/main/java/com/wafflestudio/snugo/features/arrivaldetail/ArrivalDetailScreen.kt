package com.wafflestudio.snugo.features.arrivaldetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ArrivalDetailScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "arrival detail screen",
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally),
        )
    }
}
