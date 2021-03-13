package com.mastergenova.cycleshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.StationsAPIResponse
import io.reactivex.rxjava3.subjects.Subject

class StationAdapter(private val context: Context?,
                     val onClickStationSubject: Subject<Pair<View, StationsAPIResponse?>>): RecyclerView.Adapter<StationAdapter.ViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private val listStations:ArrayList<StationsAPIResponse> = arrayListOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView
        val dockCount: TextView

        init {
            name = view.findViewById(R.id.tvNameStation)
            dockCount = view.findViewById(R.id.tvDockCountStation)
        }

        fun bindView(station: StationsAPIResponse?){
            name.text = station?.name
            dockCount.text = "Current Dock Count: " + station?.currentDockCount
            itemView.setOnClickListener {
                onClickStationSubject.onNext(Pair(itemView,station))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.card_station, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listStations[position])
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