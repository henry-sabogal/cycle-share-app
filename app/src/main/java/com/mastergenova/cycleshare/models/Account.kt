package com.mastergenova.cycleshare.models

import android.net.Uri

data class Account (
        val name:String?,
        val surname:String?,
        val displayName:String?,
        val email:String?,
        val id:String?,
        val photo:Uri?
)