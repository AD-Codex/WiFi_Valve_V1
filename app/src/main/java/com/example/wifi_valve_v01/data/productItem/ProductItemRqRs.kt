package com.example.wifi_valve_v01.data.productItem

import com.example.wifi_valve_v01.models.Product
import com.example.wifi_valve_v01.models.ProductItem

data class ProductItemRequest(
    val user_id: Int
)

data class ProductItemResponse(
    val status: Int,
    val message: String,
    val data: List<ProductItemDB>
)

data class ProductItemDB(
    val id: Long,
    val user_id: Int,
    val product_id: Long,
    val nickname: String,
    val is_online: Int,
    val state: String,
    val sensor_value: String?,  // Nullable
    val error_code: String?,    // Nullable
    val product_name : String,
    val product_image_url :  String,
    val product_description : String,
    val type: String
)


fun ProductItemDB.toProductItem(): ProductItem {
    return ProductItem(
        id = id.toString(),
        nickname = nickname,
        isOnline = is_online == 1,
        itemState = state == "1",
        sensorValue = sensor_value ?: "",
        errorCode = error_code ?: "",
        type = type,
        product = Product(
            productId = product_id.toString(),
            name = product_name,
            description = product_description,
            imageUrl = product_image_url
        )
    )

}
