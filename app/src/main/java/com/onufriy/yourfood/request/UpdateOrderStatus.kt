package com.onufriy.yourfood.request

@kotlinx.serialization.Serializable
data class UpdateOrderStatus (
    val orderid: Int,
    val status: String,
        )