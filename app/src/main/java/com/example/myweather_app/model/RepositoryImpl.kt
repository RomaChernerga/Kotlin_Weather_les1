package com.example.myweather_app.model

object RepositoryImpl : Repository {  // имплиментрируем от Repository

    private val listeners: MutableList<Repository.OnLoadListener> = mutableListOf()   // подписчики которые ожидают изменений
    private var weather: Weather? = null    // для хранения погоды

    override fun getWeatherFromServer(): Weather? = weather

    // возвращаем список росийских городов
    override fun getWeatherFromLocalStorageRus(): List<Weather>  = getRussianCities()

    // возвращаем список иностранных городов
    override fun getWeatherFromLocalStorageWorld(): List<Weather> = getWorldCities()

    override fun weatherLoaded(weather: Weather?) {
        this.weather = weather

        listeners.forEach{ it.onLoaded()}   // уведомляем, что погода получена
    }

    override fun addLoadedListener(listener: Repository.OnLoadListener) {
        listeners.add(listener)
    }

    override fun removeLoaderListener(listener: Repository.OnLoadListener) {
        listeners.remove(listener)
    }


}



