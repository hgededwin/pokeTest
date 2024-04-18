package com.ehernndez.poketest.data.persintetData

import android.app.Application

class Data : Application() {
    companion object {
        lateinit var shared: Shared
    }

    override fun onCreate() {
        super.onCreate()
        shared = Shared(applicationContext)
    }
}