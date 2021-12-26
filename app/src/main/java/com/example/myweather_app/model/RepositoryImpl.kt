package com.example.myweather_app.model

class RepositoryImpl : Repository {  // имплиментрируем от Repository

    override fun getWeatherFromServer(): Weather = Weather()

    // возвращаем список росийских городов
    override fun getWeatherFromLocalStorageRus(): List<Weather>  = getRussianCities()

    // возвращаем список иностранных городов
    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()



}



