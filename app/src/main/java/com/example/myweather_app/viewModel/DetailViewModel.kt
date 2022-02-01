package com.example.myweather_app.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweather_app.model.*
import com.example.myweather_app.view.App
import kotlin.random.Random

class DetailViewModel : ViewModel() {

    private val liveDataToObserver: MutableLiveData<AppState> = MutableLiveData()
    private val repo: Repository = RepositoryImpl         // Добавляем ранее созданный репозиторий
    private val localRepo: LocalRepository = LocalRepositoryImpl(App.getHistoryDao())        // Добавляем ранее созданный репозиторий

//    fun getData(): LiveData<AppState> = liveDataToObserver  // метод который юужет возвращать данные

    fun saveHistory(weather: Weather) {
        localRepo.saveEntity(weather)
    }

//    fun getWeather() {
//        liveDataToObserver.value = AppState.Loading  // сперва отображается загрузка при попытке запросить данные
//
//        Thread{
//
//            Thread.sleep(1000)
//
//            if(Random.nextBoolean()) {
//                val weather = repo.getWeatherFromServer() // источник, откуда берем данные
//                liveDataToObserver.postValue(AppState.Success(weather)) //загрузка успешна рандомно, если да, то передаем погоду
//            } else {
//                liveDataToObserver.postValue(AppState.Error(Exception("Проверьте интернет"))) //загрузка неуспешна и выкидывает ошибку
//            }
//        }.start()
//    }

}