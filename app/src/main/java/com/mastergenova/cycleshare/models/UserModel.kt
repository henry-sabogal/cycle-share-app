package com.mastergenova.cycleshare.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mastergenova.cycleshare.StationsAPIResponse

class UserModel : ViewModel(){
    val signOut = MutableLiveData<Boolean>()
    val selectedStation = MutableLiveData<StationsAPIResponse>()
    val selectedBike = MutableLiveData<Bike>()
    val userInfo = MutableLiveData<Account>()

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
}