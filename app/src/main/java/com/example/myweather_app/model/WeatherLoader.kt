package com.example.myweather_app.model

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.google.gson.Gson
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

object WeatherLoader {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .addInterceptor( HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BASIC)
        })
        .build()

    private const val YOUR_API_KEY = "5f2451f8-2cb0-4b16-8d3e-66d110256e12"

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

    fun loadOkHttp(city: City, listener: OnWeatherLoadListener) {

        // создаем клиент OkHttp
        val request: Request = Request.Builder()   //создаем запрос
            .get()
            .addHeader("X-Yandex-API-Key", YOUR_API_KEY)
            .url("https://api.weather.yandex.ru/v2/forecast?lat=${city.lat}&lon=${city.lon}")
            .build()

            // ЗАПРАШИВАЕМ ЗАПРОС
        client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    listener.onFailed(e)
                    Log.e("DebuagLog", "Fail Connection", e)
                }

                override fun onResponse(call: Call, response: Response) {

                    if(response.isSuccessful) {
                        // берем библиотеку Gson и просим распарсить наш Json, указывем источник и класс в который хотим преобразовать
                        val weatherDTO = Gson().fromJson(response.body?.string(), WeatherDTO::class.java)
                        listener.onLoaded(weatherDTO)
                    } else {
                        listener.onFailed(Exception(response.body?.string()))
                        Log.e("DebuagLog", "Fail Connection$response")
                    }
                }

            })

    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)    // погода загрузилась
        fun onFailed(throwable: Throwable)   // ошибка при загрузке погоды

    }

}