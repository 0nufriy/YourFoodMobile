package com.onufriy.yourfood.response



@kotlinx.serialization.Serializable
data class PassesResponse (
    val passid: Int,
    val price : Int,
    val passname: String,
    val description: String,
    val image: String?
        )