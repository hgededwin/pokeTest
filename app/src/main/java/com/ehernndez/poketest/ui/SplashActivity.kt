package com.ehernndez.poketest.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import com.ehernndez.poketest.ui.register.OnBoardingActivity
import com.ehernndez.poketest.utils.Utils
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var timer: CountDownTimer

    private val requestPersmissonNotification =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Log.e("isGranted", "true")
            } else {
                Log.e("is not Granted ---->", "false")
            }

            validateFlow()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        hideSplash()
        pushNotifications()
        // Data.shared.isRegistered = false
        // deleteDatabase("pokeTest_database")

    }

    private fun hideSplash() {
        // creating a CountdownTimer and adding 30000 millis, that is equals to 30 segs.
        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                // this function count the seconds, here we config the format that we want to show to the final user: "%02d:%02ds"

            }

            override fun onFinish() {
                // once finish the countdown is necessary to cancel the timer to stop the time.
                // here we have to enable the button to send the new code again.
                timer.cancel()

                askForNotificationsPermission()
            }
        }
        timer.start()
    }

    fun askForNotificationsPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                Log.e("isGranted", "true")
                validateFlow()
            } else {
                requestPersmissonNotification.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    fun pushNotifications() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isSuccessful) {
                val token = it.result
                Log.e("token --->", token)
            }
        }

        FirebaseMessaging.getInstance().subscribeToTopic("pokemon_go_topic")
    }
    private fun validateFlow() {
        Log.e("isRegistered --->", Data.shared.isRegistered.toString())
        if (Data.shared.isRegistered) {
            Utils().createIntent(this@SplashActivity, LoginActivity())
            finish()
        } else {
            Utils().createIntent(this@SplashActivity, OnBoardingActivity())
            finish()
        }
    }
}