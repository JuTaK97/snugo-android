package com.wafflestudio.snugo.features.onboarding

import com.wafflestudio.snugo.features.records.Building
import com.wafflestudio.snugo.features.records.Record

interface UserRepository {
    suspend fun getDepartments(): List<String>

    suspend fun signIn(
        nickname: String,
        department: String,
    )
}
