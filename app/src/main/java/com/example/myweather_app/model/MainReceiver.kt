package com.example.myweather_app.model

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class MainReceiver : BroadcastReceiver() {

    companion object {
        const val WEATHER_LOAD_SUCCESS = "WEATHER_LOAD_SUCCESS"
        const val WEATHER_LOAD_FAILED = "WEATHER_LOAD_FAILED"
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("MainReceiver", "onReceive $intent")

        when(intent.action) {
            WEATHER_LOAD_SUCCESS -> RepositoryImpl.weatherLoaded(intent.extras?.getParcelable("WEATHER_EXTRA"))
            WEATHER_LOAD_FAILED -> RepositoryImpl.weatherLoaded(null)
        }
    }
}