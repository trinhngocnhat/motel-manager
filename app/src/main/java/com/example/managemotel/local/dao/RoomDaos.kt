package com.example.managemotel.local.dao

import androidx.room.*
import com.example.managemotel.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(room: RoomEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(rooms: List<RoomEntity>)

    @Update
    suspend fun update(room: RoomEntity)

    @Delete
    suspend fun delete(room: RoomEntity)

    @Query("SELECT * FROM room_entities WHERE roomId = :id")
    suspend fun getById(id: String): RoomEntity?

    @Query("SELECT * FROM room_entities")
    fun getAll(): Flow<List<RoomEntity>>

    @Query("""
        SELECT 
            r.roomId, 
            r.typeRooms, 
            r.status, 
            r.price, 
            u.userId as tenantId,
            u.fullName as tenantName, 
            u.phone as tenantPhone, 
            m.userId as managerId,
            m.fullName as managerName,
            r.paymentStatus
        FROM room_entities r
        LEFT JOIN rental_contract_entities c ON r.roomId = c.roomId AND c.status = 'ACTIVE'
        LEFT JOIN users u ON c.userId = u.userId
        LEFT JOIN users m ON r.managerId = m.userId
    """)
    fun getAllRoomsWithTenants(): Flow<List<com.example.managemotel.models.RoomWithTenant>>
}

@Dao
interface RentalContractDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contract: RentalContractEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(contracts: List<RentalContractEntity>)

    @Update
    suspend fun update(contract: RentalContractEntity)

    @Delete
    suspend fun delete(contract: RentalContractEntity)

    @Query("SELECT * FROM rental_contract_entities WHERE contractId = :id")
    suspend fun getById(id: String): RentalContractEntity?

    @Query("SELECT * FROM rental_contract_entities")
    fun getAll(): Flow<List<RentalContractEntity>>
}

@Dao
interface MaintenanceHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(maintenance: MaintenanceHistoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(records: List<MaintenanceHistoryEntity>)

    @Update
    suspend fun update(maintenance: MaintenanceHistoryEntity)

    @Delete
    suspend fun delete(maintenance: MaintenanceHistoryEntity)

    @Query("SELECT * FROM maintenance_history_entities WHERE maintenanceId = :id")
    suspend fun getById(id: String): MaintenanceHistoryEntity?

    @Query("SELECT * FROM maintenance_history_entities")
    fun getAll(): Flow<List<MaintenanceHistoryEntity>>
}

@Dao
interface ContractTenantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contractTenant: ContractTenantEntity)

    @Query("""
        SELECT u.* FROM users u
        INNER JOIN contract_tenant_entities ct ON u.userId = ct.userId
        WHERE ct.contractId = :contractId
    """)
    fun getTenantsForContract(contractId: String): Flow<List<UserEntity>>
}
