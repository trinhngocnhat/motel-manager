package com.example.managemotel.models


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "Email")
    val username: String,
    @Json(name = "Password")
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val userId: String,
    @Json(name = "name")
    val username: String,
    @Json(name = "FullName")
    val fullName: String? = null,
    @Json(name = "Phone")
    val phone: String? = null,
    @Json(name = "Email")
    val email: String,
    @Json(name = "role")
    val role: String, // OWNER, MANAGER, TENANT
    @Json(name = "Token")
    val token: String? = null
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "success")
    val success: Boolean = true,
    @Json(name = "Message")
    val message: String? = null,
    @Json(name = "user")
    val user: UserDto? = null,
    @Json(name = "token")
    val token: String? = null
)
