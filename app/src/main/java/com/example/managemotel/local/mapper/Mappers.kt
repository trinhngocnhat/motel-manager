package com.example.managemotel.local.mapper

import com.example.managemotel.local.entity.*
import com.example.managemotel.models.*

fun User.toEntity() = UserEntity(
    userId = userId,
    username = username,
    fullName = fullName,
    phone = phone,
    email = email,
    role = role,
    createdAt = createdAt
)

fun UserEntity.toDomain() = User(
    userId = userId,
    username = username,
    password = "", // Entity doesn't store password ideally, or mapping handles it
    fullName = fullName,
    phone = phone,
    email = email,
    role = role,
    createdAt = createdAt
)

fun MotelRoom.toEntity() = RoomEntity(
    roomId = roomId,
    typeRooms = typeRooms,
    price = price,
    status = status,
    floor = floor,
    note = note,
    managerId = managerId,
    paymentStatus = paymentStatus
)

fun RoomEntity.toDomain() = MotelRoom(
    roomId = roomId,
    typeRooms = typeRooms,
    price = price,
    status = status,
    floor = floor,
    note = note,
    managerId = managerId,
    paymentStatus = paymentStatus
)

fun RentalContract.toEntity() = RentalContractEntity(
    contractId = contractId,
    userId = userId,
    roomId = roomId,
    startDate = startDate,
    endDate = endDate,
    monthlyRent = monthlyRent,
    deposit = deposit,
    status = status
)

fun RentalContractEntity.toDomain() = RentalContract(
    contractId = contractId,
    userId = userId,
    roomId = roomId,
    startDate = startDate,
    endDate = endDate,
    monthlyRent = monthlyRent,
    deposit = deposit,
    status = status
)

fun MaintenanceHistory.toEntity() = MaintenanceHistoryEntity(
    maintenanceId = maintenanceId,
    roomId = roomId,
    description = description,
    cost = cost,
    repairDate = repairDate,
    createdBy = createdBy
)

fun MaintenanceHistoryEntity.toDomain() = MaintenanceHistory(
    maintenanceId = maintenanceId,
    roomId = roomId,
    description = description,
    cost = cost,
    repairDate = repairDate,
    createdBy = createdBy
)
