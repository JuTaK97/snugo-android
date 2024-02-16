package com.wafflestudio.snugo.features.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.snugo.features.onboarding.UserViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val accessToken by userViewModel.accessToken.collectAsState()
    val nickname by userViewModel.nickname.collectAsState()
    val userDepartment by userViewModel.userDepartment.collectAsState()

    Column(
        modifier =
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "settings screen",
            modifier =
            Modifier
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text = "access token: $accessToken"
        )
        Text(
            text = "nickname: $nickname"
        )
        Text(
            text = "department: $userDepartment"
        )
    }
}
