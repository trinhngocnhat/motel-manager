package com.example.managemotel.data.repository

import com.example.managemotel.data.dao.RoomDao
import com.example.managemotel.models.Room
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val roomDao: RoomDao) {
    val allRooms: Flow<List<Room>> = roomDao.getAllRooms()

    suspend fun getRoomById(roomId: String): Room? {
        return roomDao.getRoomById(roomId)
    }

    suspend fun insertRoom(room: Room) {
        roomDao.insertRoom(room)
    }

    suspend fun updateRoom(room: Room) {
        roomDao.updateRoom(room)
    }

    suspend fun deleteRoom(room: Room) {
        roomDao.deleteRoom(room)
    }
}
