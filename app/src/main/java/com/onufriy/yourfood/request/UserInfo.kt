package com.onufriy.yourfood.request

import kotlinx.serialization.Serializable

@Serializable
data class UserInfo (
    val login: String,
    val name: String,
    val adress: String,
    val passName: String,
    val email: String,
    val phone: String,
    val fridgeId: Int,
    val role: String,
    val temperature: Double
)