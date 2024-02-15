package com.wafflestudio.snugo.features.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wafflestudio.snugo.LocalNavController
import com.wafflestudio.snugo.components.CtaButton
import com.wafflestudio.snugo.navigation.NavigationDestination
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel = hiltViewModel(),
) {
    val navController = LocalNavController.current
    val focusManager = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    var nickname by remember { mutableStateOf("") }
    val departments by userViewModel.departments.collectAsState()
    var departmentIndex by remember { mutableStateOf(0) }
    var isDepartmentMenuExpanded by remember { mutableStateOf(false) }

    val handleSignIn: suspend () -> Unit = {
        userViewModel.signIn(nickname, departmentIndex)
        navController.navigate(NavigationDestination.Home.route)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier.height(200.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "SNUGO",
                style = MaterialTheme.typography.headlineLarge,
            )
        }
        Column {
            TextField(
                value = nickname,
                onValueChange = { nickname = it },
                keyboardOptions =
                    KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                    ),
                keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) }),
                singleLine = true,
            )
            Spacer(modifier = Modifier.height(10.dp))
            ExposedDropdownMenuBox(
                expanded = isDepartmentMenuExpanded,
                onExpandedChange = { isDepartmentMenuExpanded = isDepartmentMenuExpanded.not() },
            ) {
                TextField(
                    value = departments[departmentIndex],
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDepartmentMenuExpanded) },
                )
                ExposedDropdownMenu(
                    expanded = isDepartmentMenuExpanded,
                    onDismissRequest = {
                        isDepartmentMenuExpanded = isDepartmentMenuExpanded.not()
                    },
                ) {
                    departments.forEachIndexed { idx, department ->
                        DropdownMenuItem(
                            onClick = {
                                departmentIndex = idx
                                isDepartmentMenuExpanded = false
                            },
                        ) {
                            Text(text = department)
                        }
                    }
                }
            }
        }
        CtaButton(
            onClick = {
                scope.launch {
                    handleSignIn()
                }
            },
            modifier =
                Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
        ) {
            Text(
                text = "로그인",
            )
        }
    }
}
