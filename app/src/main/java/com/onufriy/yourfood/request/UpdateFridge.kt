package com.onufriy.yourfood.request

@kotlinx.serialization.Serializable
data class UpdateFridge (
    val fridgeId: Int,
    val login: String
        )