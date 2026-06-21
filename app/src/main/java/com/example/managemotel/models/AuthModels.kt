package com.example.managemotel.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "Username")
    val username: String,
    @Json(name = "Password")
    val password: String
)

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "UserID")
    val userId: String,
    @Json(name = "Username")
    val username: String,
    @Json(name = "FullName")
    val fullName: String,
    @Json(name = "Phone")
    val phone: String?,
    @Json(name = "Email")
    val email: String,
    @Json(name = "Role")
    val role: String, // OWNER, MANAGER, TENANT
    @Json(name = "Token")
    val token: String? = null
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "Success")
    val success: Boolean,
    @Json(name = "Message")
    val message: String?,
    @Json(name = "User")
    val user: UserDto?,
    @Json(name = "Token")
    val token: String?
)
