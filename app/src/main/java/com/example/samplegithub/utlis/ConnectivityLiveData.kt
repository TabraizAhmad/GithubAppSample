package com.example.samplegithub.utlis

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

class ConnectivityLiveData(private val connectivityManager: ConnectivityManager) : LiveData<Boolean>()
{

    private val networkCallback=
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            object : ConnectivityManager.NetworkCallback() {
                override fun onUnavailable() {
                    super.onUnavailable()
                    postValue(false)
                }
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    postValue(true)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    postValue(false)
                }
            }
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActive() {
        super.onActive()
        val builder=NetworkRequest.Builder()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            connectivityManager.requestNetwork(builder.build(), networkCallback, 1000)
        }
        connectivityManager.registerNetworkCallback(builder.build(), networkCallback)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}