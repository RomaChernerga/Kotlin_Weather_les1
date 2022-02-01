package com.example.flavorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.textView).apply {
            text = BuildConfig.TYPE
            setOnClickListener{
                Buyer.buy(this@MainActivity)
            }
        }

    }
}