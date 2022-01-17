package com.example.myweather_app.view

import android.app.Application
import androidx.room.Room
import com.example.myweather_app.model.HistoryDAO
import com.example.myweather_app.model.HistoryDataBase
import java.lang.Exception

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object{
        private var appInstance: App? = null
        private var db: HistoryDataBase? = null
        private const val DB_NAME = "History.db"

        fun getHistoryDao(): HistoryDAO{

            if(db == null){
                synchronized(HistoryDataBase::class.java) {  // синхронизация работы нескольких потоков (раб по очереди)
                    if(db == null) {
                        appInstance?.let { app ->
                            db = Room.databaseBuilder(
                                app.applicationContext,
                                HistoryDataBase::class.java,
                                DB_NAME
                                // разрешить делать запросы из основного потока !(!!так лучше не делать более)
                            ).allowMainThreadQueries()
                                .build()
                        }?: throw Exception ("WTF!!")
                    }
                }
            }
            return db!!.historyDAO()
        }
    }
}