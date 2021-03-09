package com.mastergenova.cycleshare

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIClient {
    private var retrofit: Retrofit? = null

    enum class LogLevel{
        LOG_NOT_NEEDED,
        LOG_REQ_RES,
        LOG_REQ_RES_BODY_HEADERS,
        LOG_REQ_RES_HEADERS_ONLY
    }

    fun getClient(logLevel: LogLevel): Retrofit{

        val interceptor = HttpLoggingInterceptor()
        when(logLevel){
            LogLevel.LOG_NOT_NEEDED -> interceptor.level = HttpLoggingInterceptor.Level.NONE
            LogLevel.LOG_REQ_RES -> interceptor.level = HttpLoggingInterceptor.Level.BASIC
            LogLevel.LOG_REQ_RES_BODY_HEADERS -> interceptor.level = HttpLoggingInterceptor.Level.BODY
            LogLevel.LOG_REQ_RES_HEADERS_ONLY -> interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        }

        if(null == retrofit){
            val client = OkHttpClient.Builder().connectTimeout(3, TimeUnit.MINUTES)
                .writeTimeout(3, TimeUnit.MINUTES)
                .readTimeout(3, TimeUnit.MINUTES).addInterceptor(interceptor).build()

            retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(client)
                .build()
        }

        return retrofit!!
    }

    fun getAPIService(logLevel: LogLevel = LogLevel.LOG_REQ_RES_BODY_HEADERS) = getClient(logLevel).create(APIService::class.java)
}