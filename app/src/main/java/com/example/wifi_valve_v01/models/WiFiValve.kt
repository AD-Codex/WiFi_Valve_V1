package com.example.wifi_valve_v01.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

//@Parcelize
//data class WiFiValve(
//    val wifi_valve_id: String,
//    val product_id: Product,
//    val valve_nickname: String = "",
//    val valve_state: Boolean = false,
//    val is_Online: Boolean = false,//    val sensor_value: String = "",
//    val error_code: String = ""
//) : Parcelable
//
//fun  WiFiValve.toProductItem(): ProductItem {
//    return ProductItem(
//        id = wifi_valve_id,
//        product = product_id,
//        nickname = valve_nickname,
//        isOnline = is_Online,
//        extra = mapOf(
//            "valve_state" to valve_state.toString(),
//            "sensor_value" to sensor_value,
//            "error_code" to error_code
//        )
//    )
//}