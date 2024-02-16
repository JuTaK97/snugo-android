package com.wafflestudio.snugo.location

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import java.lang.ref.WeakReference
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface ILocationProvider {
    fun subscribeCurrentLocation(): Flow<LatLng>

    fun subscribePath(): Flow<List<LatLng>>
}

class LocationProvider(
    @ActivityContext context: Context,
) : ILocationProvider {
    private val weakActivity = WeakReference(context as Activity)

    override fun subscribeCurrentLocation(): Flow<LatLng> =
        callbackFlow {
            val service = startLocationService()
            service.subscribeCurrentLocation().collectLatest {
                trySend(it)
            }

            awaitClose {
                service.stopSelf()
            }
        }

    override fun subscribePath(): Flow<List<LatLng>> =
        callbackFlow {
            val list = mutableListOf<LatLng>()

            val service = startLocationService()
            service.subscribeCurrentLocation().collectLatest {
                list.add(it)
                trySend(list.toMutableList())
            }

            awaitClose {
                service.stopSelf()
            }
        }

    private suspend fun startLocationService(): LocationService =
        suspendCoroutine { continuation ->
            weakActivity.get()?.let {
                it.startService(Intent(it, LocationService::class.java))

                val connection =
                    object : ServiceConnection {
                        override fun onServiceConnected(
                            name: ComponentName?,
                            service: IBinder?,
                        ) {
                            val binder = service as LocationService.LocalBinder

                            continuation.resume(binder.getService())
                        }

                        override fun onServiceDisconnected(name: ComponentName?) {
                        }
                    }
                it.bindService(
                    Intent(it, LocationService::class.java),
                    connection,
                    Context.BIND_AUTO_CREATE,
                )
            }
        }
}
