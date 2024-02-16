package com.wafflestudio.snugo.features.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.snugo.LocalNavController
import com.wafflestudio.snugo.features.onboarding.UserViewModel
import com.wafflestudio.snugo.navigateAsOrigin
import com.wafflestudio.snugo.navigation.NavigationDestination
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val accessToken by userViewModel.accessToken.collectAsState()
    val nickname by userViewModel.nickname.collectAsState()
    val userDepartment by userViewModel.userDepartment.collectAsState()
    val scope = rememberCoroutineScope()
    val navController = LocalNavController.current

    Column(
        modifier =
        modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "settings screen",
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
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "로그아웃",
            modifier = Modifier.clickable {
                scope.launch {
                    userViewModel.signOut()
                    navController.navigateAsOrigin(NavigationDestination.Onboarding.route)
                }
            }
        )
    }
}
