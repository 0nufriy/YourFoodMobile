package com.onufriy.yourfood.request

@kotlinx.serialization.Serializable
data class UpdatePass (
    val passId: Int,
    val login: String
        )