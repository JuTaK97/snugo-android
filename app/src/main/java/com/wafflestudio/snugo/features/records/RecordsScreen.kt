package com.wafflestudio.snugo.features.records

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun RecordsScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "records screen",
            modifier =
                Modifier
                    .align(Alignment.CenterHorizontally),
        )
    }
}
