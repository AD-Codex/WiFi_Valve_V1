package com.example.wifi_valve_v01.data.auth

import android.util.Log
import com.example.wifi_valve_v01.utils.TokenManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class AuthRepository(
    private val apiService: AuthApiService,
    private val tokenManager: TokenManager
) {

    suspend fun login(username: String, password: String): Result<AuthResponse> {
        return try {
            withContext(Dispatchers.IO) {
                Log.d("AuthRepo", "Attempting login for $username")
                val response = apiService.login(LoginRequest(username, password))
                Log.d("AuthRepo", "Response: ${response.code()} | ${response.body()}")
                if (response.isSuccessful) {
                    response.body()?.let {
                        tokenManager.saveToken(it.data?.token ?: "")
                        it.data?.user?.let { user ->
                            tokenManager.saveUser(user)
                        }
                        Result.success(it)
                    } ?: Result.failure(Exception("Empty response body"))
                } else {
                    Result.failure(HttpException(response))
                }
            }
        } catch (e: IOException) {
            Result.failure(e)
        } catch (e: HttpException) {
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("AuthRepo", "Unknown error: ${e.message}")
            Result.failure(e)
        }
    }


    private fun handleAuthResponse(response: Response<AuthResponse>): Result<AuthResponse> {
        return if (response.isSuccessful) {
            response.body()?.let {
                if (it.status == 200) {
                    tokenManager.saveToken(it.data?.token ?: "")
                    Result.success(it)
                } else {
                    Result.failure(Exception(it.message))
                }
            } ?: Result.failure(Exception("Empty response"))
        } else {
            Result.failure(HttpException(response))
        }
    }
}