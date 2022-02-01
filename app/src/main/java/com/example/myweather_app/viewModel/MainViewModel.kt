package com.example.myweather_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather_app.model.Repository
import com.example.myweather_app.model.RepositoryImpl
import com.example.myweather_app.model.Weather
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl         // Добавляем ранее созданный репозиторий

    fun getData(): LiveData<AppState> = liveDataToObserver  // метод который юужет возвращать данные

    fun getWeatherFromLocalStorageRus() = getDataFromLocalSource(true)

    fun getWeatherFromLocalStorageWorld() = getDataFromLocalSource(false)

    fun getWeatherFromRemoteSource() = getDataFromLocalSource(true)

    private fun getDataFromLocalSource(isRussian: Boolean = true) {

        liveDataToObserver.value = AppState.Loading  // сперва отображается загрузка при попытке запросить данные

        Thread{
            Thread.sleep(1000)
                val weather = if(isRussian)  {    // источник, откуда берем данные если у нас русские города
                    repo.getWeatherFromLocalStorageRus()
                } else {
                    repo.getWeatherFromLocalStorageWorld()  // впротивном случае берем города иностранные
                }
                liveDataToObserver.postValue(AppState.Success(weather)) //загрузка успешна рандомно, если да, то передаем погоду
        }.start()
    }
}