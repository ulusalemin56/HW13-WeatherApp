package com.example.hw13_weatherapp.util

import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService: FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        NotificationManagerCompat
            .from(this)
            .sendNotifications(
                title = message.notification?.title.toString(),
                description = message.notification?.body.toString(),
                this
            )
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}