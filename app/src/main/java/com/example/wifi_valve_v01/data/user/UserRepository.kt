package com.example.wifi_valve_v01.data.user


import android.util.Log
import retrofit2.HttpException
import java.io.IOException

class UserRepository(private val apiService: UserApiService) {

    suspend fun updateUser(userId: Int, email: String, phone: String): Result<UpdateUserResponse> {
        return try {
            Log.d("UserRepo", "Attempting login for $userId")
            val response = apiService.updateUser(UpdateUserRequest(userId, email, phone))
            Log.d("UserRepo", "Response: ${response.code()} | ${response.body()}")
            if (response.isSuccessful) {
                response.body()?.let {
                    Result.success(it)
                } ?: Result.failure(Exception("Empty response"))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Log.e("UserRepo", "Network error", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("UserRepo", "Error updating user", e)
            Result.failure(e)
        }
    }
}
