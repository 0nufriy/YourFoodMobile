package com.onufriy.yourfood.response

@kotlinx.serialization.Serializable
data class PassInfoResponse (
    val passId: Int,
    val price: Int,
    val passName: String,
    val description: String,
    val image: String,
    val foodInfo: List<FoodInfo>
        )