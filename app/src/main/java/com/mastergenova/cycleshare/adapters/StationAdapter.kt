package com.mastergenova.cycleshare.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.StationsAPIResponse

class StationAdapter(): RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    private val listStations:ArrayList<StationsAPIResponse> = arrayListOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView
        val dockCount: TextView

        init {
            name = view.findViewById(R.id.tvNameStation)
            dockCount = view.findViewById(R.id.tvDockCountStation)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_station, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            name.text = listStations[position].name
            dockCount.text = "Current Dock Count: " + listStations[position].currentDockCount
        }
    }

    override fun getItemCount(): Int {
        return listStations.size
    }

    fun setDataset(list: List<StationsAPIResponse>){
        listStations.clear()
        listStations.addAll(list)
        notifyDataSetChanged()
    }
}