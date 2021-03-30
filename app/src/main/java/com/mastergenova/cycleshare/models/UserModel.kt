package com.mastergenova.cycleshare.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mastergenova.cycleshare.APIClient
import com.mastergenova.cycleshare.StationsAPIResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class UserModel : ViewModel(){
    val signOut = MutableLiveData<Boolean>()
    val selectedStation = MutableLiveData<StationsAPIResponse>()
    val selectedBike = MutableLiveData<Bike>()
    val userInfo = MutableLiveData<Account>()

    private val stationsList: MutableLiveData<List<StationsAPIResponse>> by lazy {
        MutableLiveData<List<StationsAPIResponse>>().also{
            loadStationsList()
        }
    }

    val selectedDate = MutableLiveData<String>()
    val selectedTime = MutableLiveData<String>()
    val toSelectedStation = MutableLiveData<StationsAPIResponse>()

    val tripAPIResponse = MutableLiveData<TripAPIResponse>()
    val refresh = MutableLiveData<Boolean>()

    fun logout(value: Boolean){
        signOut.value = value
    }

    fun selectStation(station: StationsAPIResponse){
        selectedStation.value = station
    }

    fun selectBike(bike: Bike){
        selectedBike.value = bike
    }

    fun setAccountInfo(account: Account){
        userInfo.value = account
    }

    fun setDateSelected(date: String){
        selectedDate.value = date
    }

    fun setTimeSelected(time: String){
        selectedTime.value = time
    }

    fun setRefresStationsData(refresh: Boolean){
        this.refresh.value = refresh
    }

    fun getStationsList(): LiveData<List<StationsAPIResponse>>{
        return  stationsList
    }

    fun selectToStation(station: StationsAPIResponse){
        toSelectedStation.value = station
    }

    fun bookTrip(){
        val trip = Trip(
                userInfo.value?.email!!,
                userInfo.value?.name!!,
                userInfo.value?.surname!!,
                userInfo.value?.id!!,
                userInfo.value?.displayName!!,
                selectedStation.value?.id!!,
                toSelectedStation.value?.id!!,
                selectedBike.value?.id!!,
                selectedDate.value!!,
                selectedTime.value!!
        )
        bookTripAPI(trip)
    }

    fun loadStationsList(){
        APIClient()
                .getAPIService()
                .getStations()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy (
                        onNext = { response ->
                            stationsList.value = response
                        },
                        onError = {
                            e -> e.printStackTrace()
                        }
                )
    }

    fun resetTripAPIResponse(){
        tripAPIResponse.value = TripAPIResponse(
                "",
                0,
                "",
                false
        )
    }

    private fun bookTripAPI(trip: Trip){
        APIClient()
                .getAPIService()
                .bookTrip(trip)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = { response ->
                            tripAPIResponse.value = response
                        },
                        onError = {
                            e -> e.printStackTrace()
                        }
                )
    }
}