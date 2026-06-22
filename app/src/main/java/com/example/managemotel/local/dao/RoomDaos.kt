package com.example.managemotel.local.dao

import androidx.room.*
import com.example.managemotel.local.entity.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM user_entities WHERE userId = :id")
    suspend fun getById(id: String): UserEntity?

    @Query("SELECT * FROM user_entities")
    fun getAll(): Flow<List<UserEntity>>
}

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
