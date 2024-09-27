package com.moniapps.gamificationfeature.notification

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager

class NotificationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val notificationChannel = NotificationChannel(
            "payment_alert",
            "Payment notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationChannel.description = "Payment notification channel"

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}