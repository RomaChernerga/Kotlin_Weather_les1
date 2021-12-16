package com.example.myweather_app.model

interface Repository {  // методля для баз данных

    fun getWeatherFromServer(): Weather  // метод для подгагрузки данных из сервера

    fun getWeatherFromLocalStorage(): Weather   // метод для подгагрузки данных из локального источника


}