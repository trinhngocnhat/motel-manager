package com.example.managemotel.local.repository

import com.example.managemotel.local.dao.*
import com.example.managemotel.local.entity.*
import com.example.managemotel.local.mapper.toDomain
import com.example.managemotel.local.mapper.toEntity
import com.example.managemotel.models.MotelRoom
import com.example.managemotel.models.RentalContract
import com.example.managemotel.models.MaintenanceHistory
import com.example.managemotel.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalRepository(
    private val userDao: UserDao,
    private val roomDao: RoomDao,
    private val contractDao: RentalContractDao,
    private val maintenanceDao: MaintenanceHistoryDao
) {
    // --- USERS ---
    fun getAllUsers(): Flow<List<User>> = userDao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun saveUsers(users: List<User>) = userDao.insertAll(users.map { it.toEntity() })
    suspend fun getUserById(id: String) = userDao.getById(id)?.toDomain()

    // --- ROOMS ---
    fun getAllRooms(): Flow<List<MotelRoom>> = roomDao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun saveRooms(rooms: List<MotelRoom>) = roomDao.insertAll(rooms.map { it.toEntity() })
    suspend fun getRoomById(id: String) = roomDao.getById(id)?.toDomain()

    // --- CONTRACTS ---
    fun getAllContracts(): Flow<List<RentalContract>> = contractDao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun saveContracts(contracts: List<RentalContract>) = contractDao.insertAll(contracts.map { it.toEntity() })

    // --- MAINTENANCE ---
    fun getAllMaintenance(): Flow<List<MaintenanceHistory>> = maintenanceDao.getAll().map { list -> list.map { it.toDomain() } }
    suspend fun saveMaintenance(records: List<MaintenanceHistory>) = maintenanceDao.insertAll(records.map { it.toEntity() })
}
