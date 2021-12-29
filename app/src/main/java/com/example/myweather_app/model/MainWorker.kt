package com.example.myweather_app.model

import android.content.Context
import androidx.work.*

class MainWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        var result = Result.success()

        WeatherLoader.load(city = City(), object : WeatherLoader.OnWeatherLoadListener {
            override fun onLoaded(weatherDTO: WeatherDTO) {
                result = Result.success()
            }

            override fun onFailed(throwable: Throwable) {
                result = Result.failure()
            }

        })

        return result
    }

    companion object {   // для старта WorkManager-а
        fun startWorker() {
            val uploadWorkRequest: WorkRequest =
                OneTimeWorkRequest.Builder(MainWorker::class.java)
                    .setConstraints(Constraints.Builder()
                        .setRequiresCharging(true)
                        .setRequiredNetworkType(NetworkType.UNMETERED)  // исп только безлимитный интеренет

                        .build())
                .build()
        }
    }
}