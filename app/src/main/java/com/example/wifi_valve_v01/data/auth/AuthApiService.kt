package com.example.wifi_valve_v01.data.auth


import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthApiService {

    // server ping
    @GET("ping.php")
    suspend fun ping(): Response<ResponseBody>

    // Authentication Endpoints
    @POST("api/auth/login.php")
    suspend fun login(@Body request: LoginRequest): Response<AuthResponse>


}
