package com.example.wifi_valve_v01.data.productItem

import android.util.Log
import retrofit2.HttpException
import java.io.IOException


class ProductItemRepository(private val api: ProductItemApiService) {

    suspend fun getProductItem( userId: Int ): Result<List<ProductItemDB>> {
        return try {
            Log.d("ProductItemRepo", "Attempting to get products for user_id=$userId")
            val response = api.getAllProductItem( ProductItemRequest(userId))
            Log.d("ProductItemRepo", "Response: ${response.code()} | ${response.body()}")

            if (response.isSuccessful) {
                val productResponse = response.body()
                productResponse?.let {
                    Result.success(it.data) // Extract the 'data' field (list of products)
                } ?: Result.failure(Exception("Empty response body"))
            } else {
                Result.failure(HttpException(response))
            }
        } catch (e: IOException) {
            Log.e("ProductRepo", "Network error", e)
            Result.failure(e)
        } catch (e: Exception) {
            Log.e("ProductRepo", "Unexpected error", e)
            Result.failure(e)
        }
    }
}