package com.example.myweather_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather_app.model.Repository
import com.example.myweather_app.model.RepositoryImpl
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl()         // Добавляем ранее созданный репозиторий


    fun getData(): LiveData<AppState> = liveDataToObserver  // метод который юужет возвращать данные

    fun getWeather() {  // метод который запрашивает данные
        liveDataToObserver.value = AppState.Loading  // сперва отображается загрузка при попытке запросить данные

        Thread{

            Thread.sleep(5000)

            if(Random.nextBoolean()) {
                val weather = repo.getWeatherFromServer() // источник, откуда берем данные
                liveDataToObserver.postValue(AppState.Success(weather)) //загрузка успешна рандомно, если да, то передаем погоду
            } else {
                liveDataToObserver.postValue(AppState.Error(Exception("Проверьте интернет"))) //загрузка неуспешна и выкидывает ошибку
            }

        }.start()

    }

    override fun onCleared() {
        super.onCleared()
    }


}