package com.mastergenova.cycleshare.models

import com.google.gson.annotations.SerializedName

data class Trip(
        @SerializedName("email")
        val email: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("surname")
        val surname: String,
        @SerializedName("id_gmail")
        val idGmail: String,
        @SerializedName("displayName")
        val displayName: String,
        @SerializedName("fromStation")
        val fromStation: Int,
        @SerializedName("toStation")
        val toStation: Int,
        @SerializedName("bike")
        val bike: Int,
        @SerializedName("date")
        val date: String,
        @SerializedName("time")
        val time: String
)