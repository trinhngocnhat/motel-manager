package com.example.managemotel.data.repository

import com.example.managemotel.data.dao.RoomManagementDao
import com.example.managemotel.data.dao.UserDao
import com.example.managemotel.models.*
import kotlinx.coroutines.flow.Flow

class OwnerRepository(
    private val userDao: UserDao,
    private val roomManagementDao: RoomManagementDao
) {
    val managedUsers: Flow<List<User>> = userDao.getManagedUsers()
    val tenants: Flow<List<User>> = userDao.getTenants()
    val allRooms: Flow<List<MotelRoom>> = roomManagementDao.getAllRooms()
    val allRoomTypes: Flow<List<RoomType>> = roomManagementDao.getAllRoomTypes()
    val allContracts: Flow<List<RentalContract>> = roomManagementDao.getAllContracts()

    suspend fun insertUser(user: User) = userDao.insertUser(user)
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)

    suspend fun updateRoom(room: MotelRoom) = roomManagementDao.updateRoom(room)

    suspend fun createContract(contract: RentalContract) = roomManagementDao.insertContract(contract)

    suspend fun addTenantToContract(contractId: String, userId: String) {
        roomManagementDao.addTenantToContract(ContractTenant(contractId, userId))
    }

    fun getTenantsForContract(contractId: String): Flow<List<User>> = 
        roomManagementDao.getTenantsForContract(contractId)
}
