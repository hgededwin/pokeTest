package com.ehernndez.poketest.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.ehernndez.poketest.R
import com.ehernndez.poketest.data.persistantData.Data
import java.util.concurrent.Executor

open class Utils {
    // we can use this function to enable and disable the button.
    // Is necessary set the parameter Button to access to the attributes of the class
    fun enableButton(context: Context, button: Button, isEnable: Boolean, isVariant: Boolean) {
        if (isEnable) {
            button.isEnabled = true
            if (isVariant) {
                button.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.primary_color_variant)
            } else {
                button.backgroundTintList =
                    ContextCompat.getColorStateList(context, R.color.primary_color)
            }
        } else {
            button.isEnabled = false
            button.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.btn_disabled_color)
        }
    }

    fun createIntent(context: Context, activity: Activity) {
        val intent = Intent(context, activity::class.java)
        context.startActivity(intent)
    }
}