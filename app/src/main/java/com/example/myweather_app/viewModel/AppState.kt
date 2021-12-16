package com.example.myweather_app.viewModel

import com.example.myweather_app.model.Weather

sealed class AppState {

    data class Success(val weather: Weather) : AppState()  // наследуем от AppState и пока делаем любого типа - Any
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()

}

