package com.example.myweather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather_app.R
import com.example.myweather_app.model.Weather

class MainAdapter: RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private var weather: List<Weather> = listOf()
    var listener: OnItemClick? = null

    fun setWeather(data: List<Weather>) {
        weather = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.main_item, parent, false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(weather[position]) }

    override fun getItemCount(): Int = weather.size

    inner class MainViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bind(weather: Weather) {
            itemView.apply {
                findViewById<TextView>(R.id.main_city_name_text_view).text = weather.city.name
                // при клике
                setOnClickListener {
                    listener?.onClick(weather)   // используем безопасный вызов ? = как бы проверка на нулл
                }
            }
        }
    }

    //СОЗДАЕМ МЕТОД ДЛЯ КЛИКА НА ГОРОД
    fun interface OnItemClick {
        fun onClick(weather: Weather)
    }



}
