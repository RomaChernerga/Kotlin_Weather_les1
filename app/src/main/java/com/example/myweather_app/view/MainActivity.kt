package com.example.myweather_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myweather_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // сперва тут инициализируем с lateinit - без null
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}