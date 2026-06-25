package com.example.managemotel.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val userId: String,
    val username: String,
    val fullName: String,
    val phone: String?,
    val email: String?,
    val password: String? = null,
    val role: String,
    val createdAt: String
)
