package com.wafflestudio.snugo.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.wafflestudio.snugo.MainActivity
import com.wafflestudio.snugo.R
import com.wafflestudio.snugo.location.LocationService

object ForegroundServiceConstants {
    const val CHANNEL_ID = "location_service_channel"
    const val CHANNEL_NAME = "Location Service Channel"
}

fun Service.createNotification(): Notification {
    val notificationChannel =
        NotificationChannel(
            ForegroundServiceConstants.CHANNEL_ID,
            ForegroundServiceConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        )
    val notificationManager =
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)

    // 알림 생성
    val notificationIntent = Intent(this, MainActivity::class.java)
    val pendingIntent =
        PendingIntent.getActivity(this, 1, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

    val intent =
        Intent(this, LocationService::class.java).apply {
            action = "종료"
        }
    val pendingIntent2 =
        PendingIntent.getService(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_MUTABLE,
        )

    return NotificationCompat.Builder(this, ForegroundServiceConstants.CHANNEL_ID)
        .setContentTitle("이동 기록 중...")
        .setSmallIcon(R.drawable.ic_launcher_background)
        .setContentIntent(pendingIntent)
        .addAction(R.drawable.ic_launcher_foreground, "종료", pendingIntent2)
        .build()
}
