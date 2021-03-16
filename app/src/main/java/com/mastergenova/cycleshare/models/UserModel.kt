package com.mastergenova.cycleshare.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserModel : ViewModel(){
    val signOut = MutableLiveData<Boolean>()

    fun logout(value: Boolean){
        signOut.value = value
    }
}