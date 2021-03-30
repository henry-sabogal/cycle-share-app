package com.mastergenova.cycleshare.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mastergenova.cycleshare.R
import com.mastergenova.cycleshare.models.Bike
import io.reactivex.rxjava3.subjects.Subject

class BikesByStationAdapter (private val context: Context?,
                             val onClickBikeSubject: Subject<Pair<View, Bike?>>): RecyclerView.Adapter<BikesByStationAdapter.ViewHolder>(){

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val listBikes:ArrayList<Bike> = arrayListOf()

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView
        val state: TextView

        init {
            name = view.findViewById(R.id.tvNameBike)
            state = view.findViewById(R.id.tvStateBike)
        }

        fun bindView(bike: Bike?){
            name.text = bike?.name
            state.text = bike?.state

            when(bike?.state){
                "Available" -> state.setTextColor(ContextCompat.getColor(context!!, R.color.teal_700))
                "Booked" -> state.setTextColor(ContextCompat.getColor(context!!, R.color.red))
            }
            itemView.setOnClickListener {
                onClickBikeSubject.onNext(Pair(itemView, bike))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.card_bike, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(listBikes[position])
    }

    override fun getItemCount(): Int {
        return listBikes.size
    }

    fun setDataset(list: List<Bike>){
        listBikes.clear()
        listBikes.addAll(list)
        notifyDataSetChanged()
    }
}