package com.example.wifi_valve_v01.data.product

import com.example.wifi_valve_v01.models.Product

data class ProductResponse(
    val status: Int,
    val message: String,
    val data: List<Product>
)