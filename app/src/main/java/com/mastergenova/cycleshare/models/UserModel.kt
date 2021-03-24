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

    fun getStationsList(): LiveData<List<StationsAPIResponse>>{
        return  stationsList
    }

    private fun loadStationsList(){
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
}