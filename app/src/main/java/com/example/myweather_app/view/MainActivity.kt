package com.example.myweather_app.view

import android.content.Intent
import android.media.MediaParser
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.myweather_app.R
import com.example.myweather_app.databinding.ActivityMainBinding
import com.example.myweather_app.model.MainWorker
import com.example.myweather_app.model.RepositoryImpl
import com.example.myweather_app.model.Weather
import com.google.gson.Gson
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

        val json = Gson().toJson(RepositoryImpl.getWeatherFromServer())
        Log.d("DEBUGLOG", json)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, MainFragment.newInstance())
            .commit()

        Log.d("DEBUGLOG", "startWorker")
        MainWorker.startWorker(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.contacts) {

            startActivity(Intent(this, ContactsActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        return super.onCreateOptionsMenu(menu)
    }
}





