package com.example.contentproviderclient

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

private val HISTORY_URI: Uri = Uri.parse("content://geekbrains.provider/HistoryEntity")

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cursor: Cursor? = contentResolver.query(HISTORY_URI, null,null,null,null)

        val list = mutableListOf<String>()

        cursor?.let {
            for(i in 0..cursor.count) {
                if(cursor.moveToPosition(i)) {
                        // Вытащим данные из каждого столбца курсора
                    val id = cursor.getLong(cursor.getColumnIndex("id"))
                    val city = cursor.getString(cursor.getColumnIndex("city"))
                    val temperature = cursor.getLong(cursor.getColumnIndex("temperature"))
                        //Выводим все в одну строчку
                    list.add("$id. город $city, $temperature")
                }
            }
            AlertDialog.Builder(this)
                .setItems(list.toTypedArray()) {_, _ ->}
                .setPositiveButton("OK") {_, _ -> }
                .create()
                .show()

            cursor.close()
        }

    }
}