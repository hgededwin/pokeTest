package com.ehernndez.poketest.data.persistantData

import android.content.Context
import android.content.SharedPreferences

class Shared(context: Context) {
    private val prefsName = "com.ehernndez.poketest"
    private val isRegistedKey = "is_registered_key"
    private val userNameKey = "user_name_key"
    private val lastNameKey = "last_name_key"
    private val bornDateKey = "born_date_key"
    private val emailKey = "email_key"

    private val prefs: SharedPreferences = context.getSharedPreferences(prefsName, 0)

    var isRegistered: Boolean
        get() = prefs.getBoolean(isRegistedKey, false)
        set(value) = prefs.edit().putBoolean(isRegistedKey, value).apply()

    var userName: String
        get() = prefs.getString(userNameKey, "").toString()
        set(value) = prefs.edit().putString(userNameKey, value).apply()

    var lastName: String
        get() = prefs.getString(lastNameKey, "").toString()
        set(value) = prefs.edit().putString(lastNameKey, value).apply()

    var bornDate: String
        get() = prefs.getString(bornDateKey, "").toString()
        set(value) = prefs.edit().putString(bornDateKey, value).apply()

    var email: String
        get() = prefs.getString(emailKey, "").toString()
        set(value) = prefs.edit().putString(emailKey, value).apply()
}