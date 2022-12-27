package com.onufriy.yourfood.request

import kotlinx.serialization.Serializable


@Serializable
data class RegisterRequest (
    val login: String,
    val name: String,
    val password: String,
    val email: String,
    val phone: String,
    val Role: String

)
