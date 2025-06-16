package com.example.wifi_valve_v01.models

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val productId: String,
    val name: String,
    val description: String,
    val imageId : Int
) : Parcelable