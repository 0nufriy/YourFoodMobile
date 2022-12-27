package com.onufriy.yourfood.response

@kotlinx.serialization.Serializable
data class OrdersResponse (
    val orderID: Int,
    val login: String,
    val passID: Int,
    val passName: String,
    val adress: String,
    val status: String,
    val date: String,
    val cellId: Int
    )