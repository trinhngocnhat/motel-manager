package com.example.managemotel.data.dao

import androidx.room.*
import com.example.managemotel.models.MotelRoom
import com.example.managemotel.models.RoomWithTenant
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun getAllRooms(): Flow<List<MotelRoom>>

    @Query("""
        SELECT r.roomId, r.typeRooms, r.status, r.price, r.paymentStatus, u.username as tenantName 
        FROM rooms r 
        LEFT JOIN rental_contracts c ON r.roomId = c.roomId AND c.status = 'ACTIVE' 
        LEFT JOIN users u ON c.userId = u.userId
    """)
    fun getRoomsWithTenants(): Flow<List<RoomWithTenant>>

    @Query("SELECT * FROM rooms WHERE roomId = :roomId")
    suspend fun getRoomById(roomId: String): MotelRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: MotelRoom)

    @Update
    suspend fun updateRoom(room: MotelRoom)

    @Delete
    suspend fun deleteRoom(room: MotelRoom)
}
