package com.mastergenova.cycleshare.utils

import android.content.Context
import android.net.Uri
import com.mastergenova.cycleshare.Constants

class ProfilePicture(private val context: Context?) {

    private val keyStore = "saved_uri_string_picture"

    fun storeUri(uri: Uri?){
        val sharedPref = context?.getSharedPreferences(Constants.PREFS_APP, Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putString(keyStore, uri.toString())
            apply()
        }
    }

    fun retrieveUri() :String{
        val sharedPref = context?.getSharedPreferences(Constants.PREFS_APP, Context.MODE_PRIVATE) ?: return ""
        return sharedPref.getString(keyStore, "") ?: ""
    }
}