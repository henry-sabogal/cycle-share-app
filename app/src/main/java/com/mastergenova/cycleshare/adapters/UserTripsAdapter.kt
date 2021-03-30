package com.mastergenova.cycleshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.models.TripUser
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class UserTripsAdapter(private val context: Context?): RecyclerView.Adapter<UserTripsAdapter.ViewHolder>() {

    private val inflater:LayoutInflater = LayoutInflater.from(context)
    private val listTrips:ArrayList<TripUser> = arrayListOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val id: TextView
        val state: TextView
        val date: TextView
        val time: TextView
        val bike: TextView
        val fromStation: TextView
        val toStation: TextView

        init {
            id = view.findViewById(R.id.tvId)
            state = view.findViewById(R.id.tvState)
            date = view.findViewById(R.id.tvDate)
            time = view.findViewById(R.id.tvTime)
            bike = view.findViewById(R.id.tvBike)
            fromStation = view.findViewById(R.id.tvFromStation)
            toStation = view.findViewById(R.id.tvToStation)
        }

        fun bindView(tripUser: TripUser?){
            id.text = tripUser?.id.toString()
            state.text = tripUser?.state
            date.text = formatDate(tripUser?.tripDate)
            time.text = formatTime(tripUser?.tripTime)
            bike.text = tripUser?.bikeName
            fromStation.text = tripUser?.fromStationName
            toStation.text = tripUser?.toStationName
        }

        private fun formatDate(date: String?): String{
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("yyyy-MM-dd")
            return formatter.format(parser.parse(date))
        }

        private fun formatTime(time: String?): String{
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("HH:mm")
            return formatter.format(parser.parse(time))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.card_trip, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listTrips[position])
    }

    override fun getItemCount(): Int {
        return listTrips.count()
    }

    fun setDataset(list: List<TripUser>){
        listTrips.clear()
        listTrips.addAll(list)
        notifyDataSetChanged()
    }
}