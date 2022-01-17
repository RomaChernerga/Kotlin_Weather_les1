package com.example.myweather_app.model

interface LocalRepository {

    fun getAllHistory(): List<Weather>

    fun saveEntity(weather: Weather)

}