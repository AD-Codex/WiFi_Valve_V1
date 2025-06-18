package com.example.wifi_valve_v01.ui.user


import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifi_valve_v01.data.ApiClient
import com.example.wifi_valve_v01.data.auth.AuthRepository
import com.example.wifi_valve_v01.data.user.UserRepository
import com.example.wifi_valve_v01.models.User
import com.example.wifi_valve_v01.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch



class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val tokenManager = TokenManager(application)
    private val userRepository = UserRepository(ApiClient.userApiService)
    private val authRepository = AuthRepository(ApiClient.authApiService, tokenManager)

    private val _user = MutableStateFlow(tokenManager.getUser())
    val user: StateFlow<User> = _user


    fun refreshUser() {
        _user.value = tokenManager.getUser()
    }

    fun updateUser(email: String, phone: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = userRepository.updateUser(_user.value.id, email, phone)
            if (result.isSuccess) {
                val updatedUser = _user.value.copy(email = email, phone = phone)
                tokenManager.saveUser(updatedUser)
                _user.value = updatedUser
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun login(username: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val result = authRepository.login(username, password)
            if (result.isSuccess) {
                _user.value = tokenManager.getUser()
                onResult(true)
            } else {
                onResult(false)
            }
        }
    }

    fun checkLoginStatus() {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            val user = tokenManager.getUser()

            if (!token.isNullOrEmpty() && user.id != -1) {
                try {
                    val response = ApiClient.authApiService.ping()
                    if (response.isSuccessful) {
                        _user.value = user
                        Log.d("UserViewModel", "Auto-login success: ${user.username}")
                    } else {
                        logout()
                        Log.e("UserViewModel", "Ping failed: ${response.code()}")
                    }
                } catch (e: Exception) {
                    logout()
                    Log.e("UserViewModel", "Ping exception", e)
                }
            } else {
                logout()
                Log.d("UserViewModel", "No token or invalid user")
            }
        }
    }


    fun logout() {
        tokenManager.clearToken()
        tokenManager.clearUser()
        _user.value = User(id = -1, username = "", email = "", phone = "")
    }
}