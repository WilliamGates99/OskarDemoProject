package com.xeniac.oskardemoproject.core.util

import android.content.Context
import android.os.Build
import com.xeniac.oskardemoproject.core.data.local.ConnectivityObserver
import com.xeniac.oskardemoproject.core.data.local.ConnectivityObserverImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

object NetworkObserverHelper {

    private var connectivityObserver: ConnectivityObserver? = null
    var networkStatus = ConnectivityObserver.Status.AVAILABLE

    fun observeNetworkConnection(context: Context) {
        val connectivityObserver = createConnectivityObserver(context)

        CoroutineScope(Dispatchers.IO).launch {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityObserver.observe().onEach { status ->
                    networkStatus = status
                }.launchIn(this)
            } else {
                networkStatus = ConnectivityObserver.Status.AVAILABLE
            }
        }
    }

    private fun createConnectivityObserver(context: Context): ConnectivityObserver {
        return connectivityObserver ?: ConnectivityObserverImpl(context)
    }
}