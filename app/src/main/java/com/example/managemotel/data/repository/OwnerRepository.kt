package com.example.managemotel.data.repository

import com.example.managemotel.local.dao.*
import com.example.managemotel.local.entity.*
import com.example.managemotel.local.mapper.*
import com.example.managemotel.models.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class OwnerRepository(
    private val userDao: UserDao,
    private val roomDao: RoomDao,
    private val contractDao: RentalContractDao,
    private val contractTenantDao: ContractTenantDao
) {
    val managedUsers: Flow<List<User>> = userDao.getAll().map { list ->
        list.map { it.toModel() }
    }

    val tenants: Flow<List<User>> = userDao.getAll().map { list ->
        list.map { it.toModel() }.filter { it.role == "TENANT" }
    }

    val allRooms: Flow<List<MotelRoom>> = roomDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    val allContracts: Flow<List<RentalContract>> = contractDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    suspend fun deleteUser(user: User) {
        userDao.delete(user.toEntity())
    }

    suspend fun updateRoom(room: MotelRoom) {
        roomDao.update(room.toEntity())
    }

    suspend fun addTenantToContract(contractId: String, userId: String) {
        contractTenantDao.insert(ContractTenantEntity(contractId, userId))
    }

    fun getTenantsForContract(contractId: String): Flow<List<User>> {
        return contractTenantDao.getTenantsForContract(contractId).map { list ->
            list.map { it.toModel() }
        }
    }
}
