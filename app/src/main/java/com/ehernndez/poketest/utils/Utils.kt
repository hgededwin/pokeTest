package com.ehernndez.poketest.utils

import android.content.Context
import android.widget.Button
import androidx.core.content.ContextCompat
import com.ehernndez.poketest.R

open class Utils {
    // we can use this function to enable and disable the button.
    // Is necessary set the parameter Button to access to the attributes of the class
    fun enableButton(context: Context, button: Button, isEnable: Boolean) {
        if (isEnable) {
            button.isEnabled = true
            button.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.primary_color)
        } else {
            button.isEnabled = false
            button.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.btn_disabled_color)
        }

    }
}