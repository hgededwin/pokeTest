package com.ehernndez.poketest.ui

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ehernndez.poketest.R
import com.squareup.picasso.Picasso

class NotificationActivity : AppCompatActivity() {

    lateinit var txtTitle: TextView
    lateinit var imgNotification: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        txtTitle = findViewById(R.id.txt_notification_title)
        imgNotification = findViewById(R.id.img_notification)


        val txtNotTitle = intent.getStringExtra("TITLE_MESSAGE")
        txtTitle.text = txtNotTitle

        val urlImage = intent.getStringExtra("IMAGE_MESSAGE")
        Picasso.get().load(urlImage).into(imgNotification)
    }
}