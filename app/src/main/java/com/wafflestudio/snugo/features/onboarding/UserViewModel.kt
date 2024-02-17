package com.wafflestudio.snugo.features.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel
    @Inject
    constructor(
        private val userRepository: UserRepository,
    ) : ViewModel() {
        private val _departments = MutableStateFlow<List<String>>(emptyList())
        val departments: StateFlow<List<String>> get() = _departments

        val accessToken: StateFlow<String?> =
            userRepository.accessToken
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)

        val nickname: StateFlow<String?> =
            userRepository.nickname
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)

        val userDepartment: StateFlow<String?> =
            userRepository.department
                .stateIn(viewModelScope, SharingStarted.Eagerly, null)

        init {
            viewModelScope.launch {
                fetchDepartments()
            }
        }

        suspend fun fetchDepartments() {
            _departments.value =
                userRepository.getDepartments().toMutableList().apply {
                    add(0, "단과대학 선택")
                }
        }

        suspend fun signIn(
            nickname: String,
            departmentIndex: Int,
        ) {
            userRepository.signIn(
                nickname,
                if (departmentIndex == 0) "" else _departments.value[departmentIndex],
            )
        }

        suspend fun signOut() {
            userRepository.signOut()
        }
    }
