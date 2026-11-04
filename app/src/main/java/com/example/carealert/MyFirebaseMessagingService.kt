package com.example.carealert

import android.Manifest
import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresPermission
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {
        super.onMessageReceived(remoteMessage)

        createNotificationChannel()

        val notification =
            NotificationCompat.Builder(
                this,
                "carealert_channel"
            )
                .setSmallIcon(
                    R.drawable.ic_dialog_info
                )
                .setContentTitle(
                    remoteMessage.notification?.title
                        ?: "CareAlert"
                )
                .setContentText(
                    remoteMessage.notification?.body
                        ?: ""
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .build()

        NotificationManagerCompat
            .from(this)
            .notify(
                1,
                notification
            )
    }

    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel =
                NotificationChannel(
                    "carealert_channel",
                    "CareAlert Notifications",
                    NotificationManager.IMPORTANCE_HIGH
                )

            val manager =
                getSystemService(
                    NotificationManager::class.java
                )

            manager.createNotificationChannel(
                channel
            )
        }
    }
}