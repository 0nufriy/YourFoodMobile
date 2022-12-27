package com.onufriy.yourfood.request

import kotlinx.serialization.Serializable


@Serializable
data class LoginRequest (
    val login: String,
    val password: String,
    val admin: Boolean,

)
