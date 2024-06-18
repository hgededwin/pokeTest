package com.ehernndez.poketest.utils

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.ehernndez.poketest.ui.NotificationActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.e("FCM", "Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        Looper.prepare()
        Handler().post {
            val intent = Intent(this, NotificationActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("TITLE_MESSAGE", message.notification?.title)
            intent.putExtra("IMAGE_MESSAGE", message.notification?.imageUrl.toString())
            startActivity(intent)
        }
        Looper.loop()
    }
}