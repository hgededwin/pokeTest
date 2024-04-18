package com.ehernndez.poketest.data.persintetData

import android.content.Context
import android.content.SharedPreferences

class Shared(context: Context) {
    private val prefsName = "com.ehernndez.poketest"
    private val isRegistedKey = "is_registered_key"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, 0)

    var isRegistered: Boolean
        get() = prefs.getBoolean(isRegistedKey, false)
        set(value) = prefs.edit().putBoolean(isRegistedKey, value).apply()
}