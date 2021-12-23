package com.example.myweather_app.model

class RepositoryImpl : Repository {  // имплиментрируем от Repository

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()   // возвращаем список росийских городов
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()   // возвращаем список иностранных городов
    }



}



