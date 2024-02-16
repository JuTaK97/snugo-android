package com.wafflestudio.snugo.features.onboarding

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wafflestudio.snugo.features.records.Record
import com.wafflestudio.snugo.features.records.SortMethod
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

        /*private val _myRecords = MutableStateFlow<List<Record>>(listOf())
        val myRecords = _myRecords.asStateFlow()
        suspend fun getRecord(method: SortMethod){
            when(method){
                SortMethod.BASIC -> {
                    _myRecords.value = api.getBasicRecord()
                }
                SortMethod.TOP -> {
                    _myRecords.value = api.getTopRecord()
                }
                SortMethod.RECOMMEND -> {
                    _myRecords.value = api.getRecommendedRecord()
                }
            }

        }*/
    }
