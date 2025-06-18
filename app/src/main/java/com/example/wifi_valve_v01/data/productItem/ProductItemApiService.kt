package com.example.wifi_valve_v01.data.productItem


import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProductItemApiService {
    @POST("api/product_items/get_product_items.php")
    suspend fun getAllProductItem(@Body user_id: ProductItemRequest): Response<ProductItemResponse>
}