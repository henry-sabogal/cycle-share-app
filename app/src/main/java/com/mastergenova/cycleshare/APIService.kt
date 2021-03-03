package com.mastergenova.cycleshare

import retrofit2.Call
import retrofit2.http.GET

interface APIService {

    @GET(Constants.STATIONS)
    fun getStations(): Call<StationsAPIResponse>
}