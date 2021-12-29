package com.example.myweather_app.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.stream.Collectors

object WeatherLoader {

    const val YOUR_API_KEY = "5f2451f8-2cb0-4b16-8d3e-66d110256e12"

    fun load(city: City, listener: OnWeatherLoadListener) {


            var urlConnection: HttpURLConnection? = null

            try {
                val uri = URL("https://api.weather.yandex.ru/v2/forecast?lat=${city.lat}&lon=${city.lon}")

                urlConnection = uri.openConnection() as HttpURLConnection
                urlConnection.addRequestProperty("X-Yandex-API-Key", YOUR_API_KEY)
                urlConnection.requestMethod = "GET"
                urlConnection.readTimeout = 1000
                urlConnection.connectTimeout = 1000
                val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    reader.lines().collect(Collectors.joining("\n"))
                } else {
                    ""
                }

                Log.d("Debuglog", "result: $result")

                val weatherDTO = Gson().fromJson(result, WeatherDTO::class.java)   // берем библиотеку Gson и просим распарсить наш Json, указывем источник и класс в который хотим преобразовать

                listener.onLoaded(weatherDTO)

            } catch (e: Exception) {
                listener.onFailed(e)
                Log.e("DebuagLog", "Fail Connection", e)
            } finally {
                urlConnection?.disconnect()
            }

    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)    // погода загрузилась
        fun onFailed(throwable: Throwable)   // ошибка при загрузке погоды

    }

}