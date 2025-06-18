package com.example.wifi_valve_v01.data


import com.example.wifi_valve_v01.data.auth.AuthApiService
import com.example.wifi_valve_v01.data.product.ProductApiService
import com.example.wifi_valve_v01.data.productItem.ProductItemApiService
import com.example.wifi_valve_v01.data.user.UserApiService
import com.example.wifi_valve_v01.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()
    }


    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient) // Use the timeout-enabled client here
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    val userApiService: UserApiService by lazy {
        retrofit.create(UserApiService::class.java)
    }

    val productApiService: ProductApiService by lazy {
        retrofit.create(ProductApiService::class.java)
    }

    val productItemApiService: ProductItemApiService by lazy {
        retrofit.create(ProductItemApiService::class.java)
    }

}