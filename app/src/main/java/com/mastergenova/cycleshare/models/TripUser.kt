package com.mastergenova.cycleshare.models

import com.google.gson.annotations.SerializedName

data class TripUser(
        @SerializedName("id")
        val id: Int,
        @SerializedName("state")
        val state: String,
        @SerializedName("trip_date")
        val tripDate: String,
        @SerializedName("trip_time")
        val tripTime: String,
        @SerializedName("bike")
        val bikeName: String,
        @SerializedName("from_station")
        val fromStationName: String,
        @SerializedName("to_station")
        val toStationName: String
)