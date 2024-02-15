package com.wafflestudio.snugo

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavController

val LocalNavController =
    compositionLocalOf<NavController> {
        throw IllegalStateException("value not provided")
    }
