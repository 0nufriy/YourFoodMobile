package com.onufriy.yourfood.response

import kotlinx.serialization.Serializable

@Serializable
data class FridgeResponse (
    val fridgeId: Int,
    val cellCount: Int,
    val adress: String,
    val userCount: Int
)