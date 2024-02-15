package com.wafflestudio.snugo.features.onboarding

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl
    @Inject
    constructor() : UserRepository {
        override suspend fun getDepartments(): List<String> {
            return listOf(
                "공과대학",
                "자연과학대학",
                "인문대학",
                "사회과학대학",
                "생활과학대학",
                "농업생명과학대학",
                "간호대학",
                "경영대학",
                "미술대학",
                "사범대학",
                "수의과대학",
                "약학대학",
                "음악대학",
                "의과대학",
                "자유전공학부",
                "치의학대학원",
            ).sorted()
        }

        override suspend fun signIn(
            nickname: String,
            department: String,
        ) {
            TODO("Not yet implemented")
        }
    }
