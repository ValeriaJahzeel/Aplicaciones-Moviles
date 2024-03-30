package com.example.servicio_recordatoriotareas.interfaces

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface MyLocationClient {
    fun getLocationUpdates(interval: Long): Flow<Location>

    class AnyException(message: String): Exception()
}