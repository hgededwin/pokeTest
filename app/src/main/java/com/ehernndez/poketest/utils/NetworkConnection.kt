package com.ehernndez.poketest.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Build
import androidx.lifecycle.LiveData

class NetworkConnection(private val context: Context): LiveData<Boolean>() {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var networkConnectionCallback: ConnectivityManager.NetworkCallback
    override fun onActive() {
        super.onActive()
        updateNetworkConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> {
                connectivityManager.registerDefaultNetworkCallback(createNetworkCallback())
            }

            else -> {
                context.registerReceiver(
                    networkReceiver,
                    IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
                )
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkConnectionCallback)
    }


    private fun createNetworkCallback(): ConnectivityManager.NetworkCallback {
        networkConnectionCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: android.net.Network) {
                super.onAvailable(network)
                postValue(true)
            }

            override fun onLost(network: android.net.Network) {
                super.onLost(network)
                postValue(false)
            }
        }

        return networkConnectionCallback
    }

    private fun updateNetworkConnection() {
        val activeNetwork = connectivityManager.activeNetwork
        if (activeNetwork == null) {
            postValue(false)
        } else {
            postValue(true)
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNetworkConnection()
        }
    }
}