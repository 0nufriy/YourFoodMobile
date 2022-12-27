package com.onufriy.yourfood.response

@kotlinx.serialization.Serializable
data class FoodInfo (
    val foodid: Int,
    val name: String,
    val description: String,
    val image: String
        )