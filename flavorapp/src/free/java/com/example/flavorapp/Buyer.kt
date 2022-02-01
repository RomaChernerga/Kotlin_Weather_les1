package com.example.flavorapp

import android.content.Context
import android.widget.Toast

object Buyer {

    fun buy(context: Context) {
        Toast.makeText(context, "Это бесплатно", Toast.LENGTH_LONG).show()
    }
}