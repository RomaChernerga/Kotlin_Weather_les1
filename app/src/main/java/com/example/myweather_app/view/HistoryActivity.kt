package com.example.myweather_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather_app.R
import com.example.myweather_app.model.LocalRepositoryImpl

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        findViewById<RecyclerView>(R.id.history_recycler_view).apply {
            adapter = HistoryAdapter(LocalRepositoryImpl(App.getHistoryDao()).getAllHistory()).also {
                it.notifyDataSetChanged()
            }
        }
    }
}