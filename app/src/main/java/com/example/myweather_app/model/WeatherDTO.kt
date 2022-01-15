package com.example.myweather_app.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO (
    val now: Long?,
    val fact: FactDTO?
)

data class FactDTO (   // конструктор
    @SerializedName("obs_time")
    val obs_time: Long?,
    val temp: Int?,
    @SerializedName("feels_like")
    val feels_like: Int?,
    val icon:String?,
    val condition:String?,
    val wind_speed: Double?,
    val wind_dir: String?,
    val pressure_mm: Int?,
    val humidity: Int?,
    val daytime: String?,
    val polar: Boolean?,
    val season: String?,
    val wind_gust: Double?,
)

