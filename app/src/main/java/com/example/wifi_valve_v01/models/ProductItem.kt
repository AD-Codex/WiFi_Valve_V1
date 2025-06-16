package com.example.wifi_valve_v01.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductItem(
    val id: String,
    val product: Product,
    val nickname: String = "",
    val isOnline: Boolean = true
) : Parcelable