package com.example.myweather_app.view

import android.media.MediaParser
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

import com.example.myweather_app.databinding.ActivityMainBinding
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.util.*
import java.util.stream.Collector
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {

    // сперва тут инициализируем с lateinit - без null
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

////        binding.url.setText("https://www.yandex.ru/")
////
////
////
////        binding.ok.setOnClickListener{
////
////            Thread {
////                var urlConnection: HttpURLConnection? = null
////                val handler = Handler(Looper.getMainLooper())
////
////                try {
////                    val uri = URL(binding.url.text.toString())
////
////                    urlConnection = uri.openConnection() as HttpURLConnection
////                    urlConnection.requestMethod = "GET"
////                    urlConnection.readTimeout = 1000
////                    urlConnection.connectTimeout = 1000
////                    val reader = BufferedReader(InputStreamReader(urlConnection.inputStream))
////                    val result = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
////                        reader.lines().collect(Collectors.joining("\n"))
////                    } else {
////                        ""
////                    }
////
////                    Log.d("Debuglog", "result: $result")
////
////                    handler.post{
////                        binding.webview.loadDataWithBaseURL(null,result,"text/html; charset=utf-8", Charsets.UTF_8.toString(), null)  // просим выполнить в основном потоке
////                    }
////
////
////
////                } catch (e: Exception) {
////                    Log.e("DebuagLog", "Fail Connection", e)
////                } finally {
////                    urlConnection?.disconnect()
////                }
////            }. start()
//
//
//
//        }

    }
}





