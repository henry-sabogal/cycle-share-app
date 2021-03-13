package com.mastergenova.cycleshare.models

import com.google.gson.annotations.SerializedName
import com.mastergenova.cycleshare.StationsAPIResponse

class BikesByStation (
        @SerializedName("id")
        val id:Int,
        @SerializedName("name")
        val name:String,
        @SerializedName("lon")
        val lon:Double,
        @SerializedName("lat")
        val lat:Double,
        @SerializedName("current_dockCount")
        val currentDockCount:Int,
        @SerializedName("bikes")
        val bikes:ArrayList<Bike>
)