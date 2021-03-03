package com.mastergenova.cycleshare

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit{

        val interceptor = HttpLoggingInterceptor()

        if(null == retrofit){
            val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).addInterceptor(interceptor).build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!
    }

    fun getAPIService() = getClient().create(APIService::class.java)
}