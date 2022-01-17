package com.example.myweather_app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myweather_app.R
import com.example.myweather_app.model.Weather

class HistoryAdapter(private var items: List<Weather>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item_layout, parent,false)
        )


    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {

        val weather = items[position]

        holder.itemView.apply {
            findViewById<TextView>(R.id.city_label).text = weather.city.name
            findViewById<TextView>(R.id.temperature_label).text = weather.temperature.toString()
            findViewById<TextView>(R.id.condition_label).text = weather.condition
        }
    }

    override fun getItemCount(): Int =items.size

    class HistoryItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }
}