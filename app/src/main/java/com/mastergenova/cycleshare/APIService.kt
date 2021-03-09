package com.mastergenova.cycleshare

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface APIService {

    @GET(Constants.STATIONS)
    fun getStations(): Observable<List<StationsAPIResponse>>
}