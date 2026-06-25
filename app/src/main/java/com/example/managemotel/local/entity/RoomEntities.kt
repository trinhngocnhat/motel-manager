package com.example.managemotel.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "room_type_entities")
data class RoomTypeEntity(
    @PrimaryKey
    val typeId: String,
    val typeName: String,
    val price: Double,
    val description: String?
)

@Entity(tableName = "room_entities")
data class RoomEntity(
    @PrimaryKey
    val roomId: String,
    val typeRooms: String,
    val price: Double,
    val status: String,
    val floor: Int,
    val note: String?,
    val managerId: String?,
    val paymentStatus: String?
)

@Entity(
    tableName = "rental_contract_entities",
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class RentalContractEntity(
    @PrimaryKey
    val contractId: String,
    val userId: String,
    val roomId: String,
    val startDate: String,
    val endDate: String?,
    val monthlyRent: Double,
    val deposit: Double,
    val status: String
)

@Entity(
    tableName = "contract_tenant_entities",
    primaryKeys = ["contractId", "userId"],
    foreignKeys = [
        ForeignKey(
            entity = RentalContractEntity::class,
            parentColumns = ["contractId"],
            childColumns = ["contractId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ContractTenantEntity(
    val contractId: String,
    val userId: String
)

@Entity(
    tableName = "maintenance_history_entities",
    foreignKeys = [
        ForeignKey(
            entity = RoomEntity::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["userId"],
            childColumns = ["createdBy"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MaintenanceHistoryEntity(
    @PrimaryKey
    val maintenanceId: String,
    val roomId: String,
    val description: String,
    val cost: Double,
    val repairDate: String,
    val createdBy: String?
)
