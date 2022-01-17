package com.example.myweather_app.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity   // при помощи этой натации создается таблица
data class HistoryEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val city: String,
    val temperature: Int,
    val condition: String,
    val timestamp: Long
)