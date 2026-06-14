package com.example.managemotel.data.dao

import androidx.room.*
import com.example.managemotel.models.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE role IN ('MANAGER', 'TENANT')")
    fun getManagedUsers(): Flow<List<User>>

    @Query("SELECT * FROM users WHERE role = 'TENANT'")
    fun getTenants(): Flow<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)
}

@Dao
interface RoomManagementDao {
    @Query("SELECT * FROM rooms")
    fun getAllRooms(): Flow<List<MotelRoom>>

    @Query("SELECT * FROM room_types")
    fun getAllRoomTypes(): Flow<List<RoomType>>

    @Update
    suspend fun updateRoom(room: MotelRoom)

    @Query("SELECT * FROM rental_contracts")
    fun getAllContracts(): Flow<List<RentalContract>>

    @Query("SELECT * FROM rental_contracts WHERE roomId = :roomId AND status = 'ACTIVE' LIMIT 1")
    suspend fun getActiveContractForRoom(roomId: String): RentalContract?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertContract(contract: RentalContract)

    @Update
    suspend fun updateContract(contract: RentalContract)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTenantToContract(contractTenant: ContractTenant)

    @Query("SELECT u.* FROM users u INNER JOIN contract_tenants ct ON u.userId = ct.userId WHERE ct.contractId = :contractId")
    fun getTenantsForContract(contractId: String): Flow<List<User>>
}
