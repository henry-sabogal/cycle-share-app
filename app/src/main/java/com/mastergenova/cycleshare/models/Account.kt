package com.mastergenova.cycleshare.models

import android.net.Uri

data class Account (
        val displayName:String?,
        val email:String?,
        val id:String?,
        val photo:Uri?
)