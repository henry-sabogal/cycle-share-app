package com.mastergenova.cycleshare.models

import com.google.gson.annotations.SerializedName

data class TripAPIResponse(
        @SerializedName("error")
        val error: String,
        @SerializedName("id")
        val id: Int,
        @SerializedName("email")
        val email: String,
        @SerializedName("success")
        val success: Boolean
)