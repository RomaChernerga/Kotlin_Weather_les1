package com.example.myweather_app.model

interface Repository {  // методля для баз данных

    fun getWeatherFromServer(): Weather?  // метод для подгагрузки данных из сервера
    fun getWeatherFromLocalStorageRus(): List<Weather>   // метод для подгагрузки данных из локального источника
    fun getWeatherFromLocalStorageWorld(): List<Weather>   // метод для подгагрузки данных из локального источника

    fun weatherLoaded(weather: Weather?)
    fun addLoadedListener(listener: OnLoadListener)
    fun removeLoaderListener(listener: OnLoadListener)


    fun interface OnLoadListener { fun onLoaded() }


}