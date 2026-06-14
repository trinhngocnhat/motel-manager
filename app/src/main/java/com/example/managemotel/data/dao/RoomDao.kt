package com.example.managemotel.data.dao

import androidx.room.*
import com.example.managemotel.models.MotelRoom
import kotlinx.coroutines.flow.Flow

@Dao
interface RoomDao {
    @Query("SELECT * FROM rooms")
    fun getAllRooms(): Flow<List<MotelRoom>>

    @Query("SELECT * FROM rooms WHERE roomId = :roomId")
    suspend fun getRoomById(roomId: String): MotelRoom?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRoom(room: MotelRoom)

    @Update
    suspend fun updateRoom(room: MotelRoom)

    @Delete
    suspend fun deleteRoom(room: MotelRoom)
}
