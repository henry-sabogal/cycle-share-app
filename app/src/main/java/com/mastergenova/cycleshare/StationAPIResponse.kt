package com.mastergenova.cycleshare

import com.google.gson.annotations.SerializedName

class StationsAPIResponse(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("lon")
    val lon:Double,
    @SerializedName("lat")
    val lat:Double,
    @SerializedName("current_dockCount")
    val currentDockCount:Int
) {
}