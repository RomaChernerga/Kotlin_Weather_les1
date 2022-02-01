package com.example.myweather_app.model

import android.os.Build
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors


object WeatherLoader {

    private val client: OkHttpClient = OkHttpClient.Builder()
        .callTimeout(1000, TimeUnit.MILLISECONDS)
        .connectTimeout(1000, TimeUnit.MILLISECONDS)
        .addInterceptor(Interceptor { chain ->
            chain.proceed(chain.request()
                .newBuilder()
                .addHeader("X-Yandex-API-Key", YOUR_API_KEY)
                .build())
        })
        .addInterceptor( HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
        .build()

    private val weatherAPI: WeatherAPI = Retrofit.Builder()
        .baseUrl("https://api.weather.yandex.ru/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()
        .create(WeatherAPI::class.java)

    private const val YOUR_API_KEY = "5f2451f8-2cb0-4b16-8d3e-66d110256e12"

    fun load(city: City, listener: OnWeatherLoadListener) {

            var urlConnection: HttpURLConnection? = null

            try {
                val uri = URL("https://api.weather.yandex.ru/v2/informers?lat=${city.lat}&lon=${city.lon}")

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

    fun loadRetrofit(city: City, listener: OnWeatherLoadListener) {
        // создаем клиент RetroFit
        weatherAPI.getWeather(city.lat, city.lon)
            .enqueue(object: retrofit2.Callback<WeatherDTO>{
                override fun onResponse(
                    call: retrofit2.Call<WeatherDTO>,
                    response: retrofit2.Response<WeatherDTO>
                ) {
                    if(response.isSuccessful) {
                        response.body()?.let { listener.onLoaded(it) }
                    } else {
                        listener.onFailed(Exception(response.message()))
                        Log.e("DebuagLog", "Fail Connection$response")
                    }
                }
                override fun onFailure(call: retrofit2.Call<WeatherDTO>, t: Throwable) {
                    listener.onFailed(t)
                }
            })
    }

    interface OnWeatherLoadListener {
        fun onLoaded(weatherDTO: WeatherDTO)    // погода загрузилась
        fun onFailed(throwable: Throwable)   // ошибка при загрузке погоды

    }

}