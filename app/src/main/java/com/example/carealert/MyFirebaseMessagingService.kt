package com.example.carealert

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    override fun onMessageReceived(
        remoteMessage: RemoteMessage
    ) {

        createNotificationChannel()

        val builder =
            NotificationCompat.Builder(
                this,
                "carealert_channel"
            )
                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )
                .setContentTitle(
                    remoteMessage.notification?.title
                )
                .setContentText(
                    remoteMessage.notification?.body
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )

        NotificationManagerCompat
            .from(this)
            .notify(
                1,
                builder.build()
            )
    }

    private fun createNotificationChannel() {

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {

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