package com.wafflestudio.snugo.features.onboarding

interface UserRepository {
    suspend fun getDepartments(): List<String>

    suspend fun signIn(
        nickname: String,
        department: String,
    )
}
