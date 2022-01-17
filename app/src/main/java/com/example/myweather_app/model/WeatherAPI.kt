package com.example.myweather_app.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI
{
    @GET("v2/forecast")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
    ): Call<WeatherDTO>

}

