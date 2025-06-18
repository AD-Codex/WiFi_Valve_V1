package com.example.wifi_valve_v01.data.product

import android.util.Log
import com.example.wifi_valve_v01.models.Product
import retrofit2.HttpException
import java.io.IOException


class ProductRepository(private val api: ProductApiService) {

    suspend fun fetchProducts(): Result<List<Product>> {
        return try {
            Log.d("ProductRepo", "Attempting to get products")
            val response = api.getProducts()
            Log.d("ProductRepo", "Response: ${response.code()} | ${response.body()}")

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