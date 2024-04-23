package com.ehernndez.poketest.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persintetData.Data
import com.ehernndez.poketest.ui.register.OnBoardingActivity
import com.ehernndez.poketest.utils.Utils
import java.util.concurrent.TimeUnit

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        hideSplash()

        Data.shared.isRegistered = true
    }

   private fun hideSplash() {
        // creating a CountdownTimer and adding 30000 millis, that is equals to 30 segs.
        timer = object : CountDownTimer(5000, 1) {
            override fun onTick(millisUntilFinished: Long) {
                // this function count the seconds, here we config the format that we want to show to the final user: "%02d:%02ds"
                val seconds = String.format(
                    "%02d:%02ds",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(
                                    millisUntilFinished
                                )
                            )
                )
            }

            override fun onFinish() {
                // once finish the countdown is necessary to cancel the timer to stop the time.
                // here we have to enable the button to send the new code again.
                timer.cancel()

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
        timer.start()
    }
}