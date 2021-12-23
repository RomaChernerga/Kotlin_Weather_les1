package com.example.myweather_app.viewModel

sealed class AppState {
        // ШАБЛОННЫЙ ТИП КОТОРЫЙ ПОДОЙДЕТ ДЛЯ ЛЮБОГО НАШЕГО ТИПА ЭКРАНА
    data class Success<T>(val data: T) : AppState()  // наследуем от AppState и пока делаем любого типа - Any
    data class Error(val error: Throwable) : AppState()  // <Nothing>  - значит, даже не используется
    object Loading : AppState()

}

