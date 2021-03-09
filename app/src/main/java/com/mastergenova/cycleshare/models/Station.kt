package com.mastergenova.cycleshare.models

import java.io.Serializable

data class Station(
        val id: Int,
        val name: String,
        val lon: Float,
        val lat: Float,
        val currentDockCount: Int
):Serializable