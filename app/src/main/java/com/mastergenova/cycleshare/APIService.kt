package com.mastergenova.cycleshare

import com.mastergenova.cycleshare.models.BikesByStation
import com.mastergenova.cycleshare.models.Trip
import com.mastergenova.cycleshare.models.TripAPIResponse
import com.mastergenova.cycleshare.models.TripUser
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIService {

    @GET(Constants.STATIONS)
    fun getStations(): Observable<List<StationsAPIResponse>>

    @GET(Constants.BIKESBYSTATION)
    fun getBikesByStation(@Path("id") id: Int): Observable<BikesByStation>

    @POST(Constants.BOOKTRIP)
    fun bookTrip(@Body trip: Trip): Observable<TripAPIResponse>

    @GET(Constants.TRIPSBYUSER)
    fun tripsByUser(@Path("id_gmail") idGmail: String?): Observable<List<TripUser>>
}