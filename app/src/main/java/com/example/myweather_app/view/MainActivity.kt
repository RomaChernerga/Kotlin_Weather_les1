package com.example.myweather_app.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.example.myweather_app.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    // сперва тут инициализируем с lateinit - без null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // ЛЯМДЫ
//        Handler(Looper.getMainLooper())
//            .post(object : Runnable {
//                override fun run() {
//                    TODO("Not yet implemented")
//                }
//            })
        //__________
//        Handler(Looper.getMainLooper())
//            .post(Runnable {
//                TODO("Not yet implemented")
//            })
        //___
//        Handler(Looper.getMainLooper())
//            .post { TODO("Not yet implemented") }
    }


}

//fun getDefaultLocale(deliveryArea: String): Locale =
//    when(deliveryArea.lowercase()) {
//        "german", "austria" -> Locale.GERMAN
//        "usa", "usa" -> Locale.ENGLISH
//        "france" -> Locale.FRENCH
//        "russian" -> Locale.getDefault()
//        else -> Locale.ENGLISH
//    }



