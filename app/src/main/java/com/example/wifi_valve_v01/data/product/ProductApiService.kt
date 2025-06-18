package com.example.wifi_valve_v01.data.product

import retrofit2.Response
import retrofit2.http.GET

interface ProductApiService {
    @GET("api/product/get_products.php")
    suspend fun getProducts(): Response<ProductResponse>
}