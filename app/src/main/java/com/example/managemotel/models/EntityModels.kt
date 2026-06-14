package com.example.managemotel.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val userId: String,
    val username: String,
    val password: String,
    val fullName: String,
    val phone: String?,
    val email: String?,
    val role: String, // OWNER, MANAGER, TENANT
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "room_types")
data class RoomType(
    @PrimaryKey
    val typeId: String,
    val typeName: String,
    val price: Double,
    val description: String
)

@Entity(
    tableName = "rooms",
    foreignKeys = [
        ForeignKey(
            entity = RoomType::class,
            parentColumns = ["typeId"],
            childColumns = ["typeId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class MotelRoom(
    @PrimaryKey
    val roomId: String,
    val typeId: String,
    val status: String, // AVAILABLE, OCCUPIED, MAINTENANCE
    val floor: Int,
    val note: String?
)

@Entity(
    tableName = "rental_contracts",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MotelRoom::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RentalContract(
    @PrimaryKey
    val contractId: String,
    val userId: String, // Primary Tenant
    val roomId: String,
    val startDate: String,
    val endDate: String,
    val monthlyRent: Double,
    val deposit: Double,
    val status: String // ACTIVE, EXPIRED
)

@Entity(
    tableName = "contract_tenants",
    primaryKeys = ["contractId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = RentalContract::class,
            parentColumns = ["contractId"],
            childColumns = ["contractId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ContractTenant(
    val contractId: String,
    val userId: String
)
