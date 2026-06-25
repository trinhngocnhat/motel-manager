package com.example.managemotel.local.mapper

import com.example.managemotel.domain.enums.UserRole
import com.example.managemotel.domain.model.User
import com.example.managemotel.local.entity.UserEntity
import com.example.managemotel.models.UserDto

fun UserDto.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        username = this.username,
        fullName = this.fullName ?: this.username,
        phone = this.phone,
        email = this.email,
        password = null,
        role = this.role.uppercase(),
        createdAt = java.time.LocalDateTime.now().toString()
    )
}

fun UserEntity.toDomain(): User {
    return User(
        userId = this.userId,
        username = this.username,
        fullName = this.fullName,
        email = this.email ?: "",
        phone = this.phone ?: "",
        role = UserRole.valueOf(this.role.uppercase())
    )
}

fun UserEntity.toModel(): com.example.managemotel.models.User {
    return com.example.managemotel.models.User(
        userId = this.userId,
        username = this.username,
        password = this.password ?: "",
        fullName = this.fullName,
        phone = this.phone,
        email = this.email,
        role = this.role,
        createdAt = this.createdAt
    )
}

fun User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        username = this.username,
        fullName = this.fullName,
        phone = this.phone,
        email = this.email,
        password = null,
        role = this.role.name,
        createdAt = java.time.LocalDateTime.now().toString()
    )
}

fun com.example.managemotel.models.User.toEntity(): UserEntity {
    return UserEntity(
        userId = this.userId,
        username = this.username,
        fullName = this.fullName,
        phone = this.phone,
        email = this.email,
        password = this.password,
        role = this.role,
        createdAt = this.createdAt
    )
}
