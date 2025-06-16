package com.example.wifi_valve_v01.utils

import android.content.Context
import com.example.wifi_valve_v01.models.User


class TokenManager(context: Context) {
    private val sharedPref = context.getSharedPreferences(
        Constants.AUTH_PREFERENCES,
        Context.MODE_PRIVATE
    )

    fun saveToken(token: String) {
        sharedPref.edit().putString(Constants.JWT_TOKEN_KEY, token).apply()
    }
    fun getToken(): String? {
        return sharedPref.getString(Constants.JWT_TOKEN_KEY, null)
    }
    fun clearToken() {
        sharedPref.edit().remove(Constants.JWT_TOKEN_KEY).apply()
    }



    fun saveUser(user: User) {
        sharedPref.edit().apply {
            putInt("user_id", user.id)
            putString("username", user.username)
            putString("email", user.email)
            putString("phone_number", user.phone)
            apply()
        }
    }
    fun getUser(): User {
        return User(
            id = sharedPref.getInt("user_id", -1),
            username = sharedPref.getString("username", "") ?: "",
            email = sharedPref.getString("email", "") ?: "",
            phone = sharedPref.getString("phone_number", "") ?: ""
        )
    }
    fun clearUser() {
        sharedPref.edit().apply {
            remove("user_id")
            remove("username")
            remove("email")
            remove("phone_number")
            apply()
        }
    }


}