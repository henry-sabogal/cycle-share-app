package com.mastergenova.cycleshare

import com.mastergenova.cycleshare.models.BikesByStation
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET(Constants.STATIONS)
    fun getStations(): Observable<List<StationsAPIResponse>>

    @GET(Constants.BIKESBYSTATION)
    fun getBikesByStation(@Path("id") id: Int): Observable<BikesByStation>
}