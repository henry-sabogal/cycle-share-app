package com.mastergenova.cycleshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.StationsAPIResponse

class SpinnerStationsAdapter(context:Context?,
                                private val stations: List<StationsAPIResponse>): BaseAdapter() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return stations.count()
    }

    override fun getItem(position: Int): Any {
        return stations[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val root = inflater.inflate(R.layout.card_station, parent, false)
        val nameStation = root.findViewById<TextView>(R.id.tvNameStation)
        val dockCountStation = root.findViewById<TextView>(R.id.tvDockCountStation)

        nameStation.text = stations[position].name
        dockCountStation.text = "Current Dock Count: " + stations[position].currentDockCount

        return root
    }
}