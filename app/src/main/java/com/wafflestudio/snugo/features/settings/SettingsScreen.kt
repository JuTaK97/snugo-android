package com.wafflestudio.snugo.features.settings

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.snugo.LocalNavController
import com.wafflestudio.snugo.R
import com.wafflestudio.snugo.components.TopBar
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
        modifier = modifier.fillMaxSize(),
    ) {
        TopBar(
            title = {
                Text(
                    text = "설정",
                    style =
                        MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                )
            },
        )
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .border(
                            width = 0.5.dp,
                            color = MaterialTheme.colorScheme.outline,
                            shape = RoundedCornerShape(10.dp),
                        )
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_person),
                    contentDescription = "profile icon",
                    modifier =
                        Modifier
                            .border(
                                width = 0.5.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = CircleShape,
                            )
                            .padding(5.dp),
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        text = nickname ?: "",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = userDepartment ?: "",
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "로그아웃",
                modifier =
                    Modifier.clickable {
                        scope.launch {
                            userViewModel.signOut()
                            navController.navigateAsOrigin(NavigationDestination.Onboarding.route)
                        }
                    },
            )
        }
    }
}
