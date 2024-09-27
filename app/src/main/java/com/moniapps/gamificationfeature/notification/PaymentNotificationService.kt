package com.moniapps.gamificationfeature.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.moniapps.gamificationfeature.R
import kotlin.random.Random


class PaymentNotificationService(
    private val  context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    fun showNotification(title: String, message: String){
        val notification = NotificationCompat.Builder(context, "payment_alert")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setPriority(NotificationManager.IMPORTANCE_HIGH)
            .setAutoCancel(true)
            .build()
        notificationManager.notify(Random.nextInt(),notification)
    }
}