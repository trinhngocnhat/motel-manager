package com.example.managemotel.data.repository

import com.example.managemotel.data.dao.RoomDao
import com.example.managemotel.models.MotelRoom
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val roomDao: RoomDao) {
    val allRooms: Flow<List<MotelRoom>> = roomDao.getAllRooms()
    val allRoomsWithTenants: Flow<List<com.example.managemotel.models.RoomWithTenant>> = roomDao.getRoomsWithTenants()

    suspend fun getRoomById(roomId: String): MotelRoom? {
        return roomDao.getRoomById(roomId)
    }

    suspend fun insertRoom(room: MotelRoom) {
        roomDao.insertRoom(room)
    }

    suspend fun updateRoom(room: MotelRoom) {
        roomDao.updateRoom(room)
    }

    suspend fun deleteRoom(room: MotelRoom) {
        roomDao.deleteRoom(room)
    }
}
