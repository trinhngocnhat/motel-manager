package com.example.managemotel.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// ==========================================
// USERS
// ==========================================

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

    val createdAt: String // SQL DATETIME
)

// ==========================================
// ROOMS
// ==========================================

@Entity(tableName = "rooms")
data class MotelRoom(

    @PrimaryKey
    val roomId: String,

    val typeRooms: String, // Phòng thường, Phòng tốt

    val price: Double,

    val status: String, // AVAILABLE, OCCUPIED, MAINTENANCE

    val floor: Int,

    val note: String?,

    val managerId: String?,

    val paymentStatus: String? = null
)

// ==========================================
// ROOM TYPES
// ==========================================

@Entity(tableName = "room_types")
data class RoomType(
    @PrimaryKey
    val typeId: String,
    val typeName: String,
    val price: Double,
    val description: String?
)

// ==========================================
// ROOM WITH TENANT (FOR UI DISPLAY)
// ==========================================

data class RoomWithTenant(

    val roomId: String,

    val typeRooms: String,

    val status: String,

    val price: Double,

    val tenantName: String?,

    val tenantPhone: String?,

    val paymentStatus: String?
)

// ==========================================
// RENTAL CONTRACTS
// ==========================================

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

    val userId: String,

    val roomId: String,

    val startDate: String,

    val endDate: String?,

    val monthlyRent: Double,

    val deposit: Double,

    val status: String // ACTIVE, EXPIRED
)

// ==========================================
// CONTRACT TENANTS (MANY-TO-MANY)
// ==========================================

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

// ==========================================
// MAINTENANCE HISTORY
// ==========================================

@Entity(
    tableName = "maintenance_history",
    foreignKeys = [

        ForeignKey(
            entity = MotelRoom::class,
            parentColumns = ["roomId"],
            childColumns = ["roomId"],
            onDelete = ForeignKey.CASCADE
        ),

        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["createdBy"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MaintenanceHistory(

    @PrimaryKey
    val maintenanceId: String,

    val roomId: String,

    val description: String,

    val cost: Double,

    val repairDate: String,

    val createdBy: String?
)