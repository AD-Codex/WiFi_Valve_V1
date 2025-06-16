package com.example.wifi_valve_v01.data.auth

import com.example.wifi_valve_v01.models.User

data class LoginRequest(
    val username: String,
    val password: String
)

data class AuthResponse(
    val status: Int,
    val message: String,
    val data: AuthData?
) {
    data class AuthData(
        val token: String,
        val user: User
    )
}