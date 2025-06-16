package com.example.wifi_valve_v01.data.user

// UpdateUserRequest.kt
data class UpdateUserRequest(
    val id: Int,
    val email: String,
    val phone: String
)

// UpdateUserResponse.kt
data class UpdateUserResponse(
    val status: Int,
    val message: String
)