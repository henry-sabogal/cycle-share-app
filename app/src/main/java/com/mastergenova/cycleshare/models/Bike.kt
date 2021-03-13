package com.mastergenova.cycleshare.models

import com.google.gson.annotations.SerializedName

data class Bike(
    @SerializedName("id")
    val id:Int,
    @SerializedName("name")
    val name:String,
    @SerializedName("state")
    val state:String
)