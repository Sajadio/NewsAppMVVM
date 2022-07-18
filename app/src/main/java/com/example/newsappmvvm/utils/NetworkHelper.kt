package com.example.newsappmvvm.utils

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import com.example.newsappmvvm.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import javax.net.SocketFactory

class NetworkHelper(private val application: Application, context: Context) : LiveData<Int>() {

    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    init {
        if (!hasInternetConnection())
            postValue(R.string.save_label)
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = application.getSystemService(
            Context.CONNECTIVITY_SERVICE,
        ) as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    override fun onActive() {
        networkCallback = createNetworkCallback()
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        cm.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onInactive() {
        cm.unregisterNetworkCallback(networkCallback)
    }

    private fun createNetworkCallback() = object : ConnectivityManager.NetworkCallback() {

        override fun onAvailable(network: Network) {
            val networkCapabilities = cm.getNetworkCapabilities(network)
            val hasInternetCapability =
                networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)

            if (hasInternetCapability == true) {
                CoroutineScope(Dispatchers.IO).launch {

                    val hasInternet = DoesNetworkHaveInternet.execute(network.socketFactory)
                    withContext(Dispatchers.Main) {
                        if (hasInternet) {
                            postValue(R.string.save_label)
                        } else
                            postValue(R.string.save_label)
                    }
                }
            }
        }

        override fun onLost(network: Network) {
//            postValue(R.string.noConnection)
        }
    }
}

object DoesNetworkHaveInternet {

    fun execute(socketFactory: SocketFactory): Boolean {
        return try {
            val socket = socketFactory.createSocket() ?: throw IOException("Socket is null.")
            socket.connect(InetSocketAddress("8.8.8.8", 53), 1500)
            socket.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}