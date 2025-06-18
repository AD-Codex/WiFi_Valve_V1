package com.example.wifi_valve_v01.data.user


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApiService {

    // User Update
    @POST("api/user/update_user.php")
    suspend fun updateUser(@Body request: UpdateUserRequest): Response<UpdateUserResponse>


}