package com.example.myweather_app.model

class RepositoryImpl : Repository {  // имплиментрируем от Repository

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorage(): Weather {
        return Weather()
    }

}



