package com.example.wifi_valve_v01.models

import android.os.Parcelable
import com.example.wifi_valve_v01.utils.Constants
import kotlinx.parcelize.Parcelize
import com.google.gson.annotations.SerializedName

@Parcelize
data class Product(
    @SerializedName("id") val productId: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("image_url") val imageUrl: String
) : Parcelable {
    val fullImageUrl: String
        get() = Constants.BASE_URL + imageUrl
}