package com.example.managemotel.data.repository

import com.example.managemotel.local.dao.RoomDao
import com.example.managemotel.local.mapper.toDomain
import com.example.managemotel.local.mapper.toEntity
import com.example.managemotel.models.MotelRoom
import com.example.managemotel.models.RoomWithTenant
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomRepository(private val roomDao: RoomDao) {
    val allRooms: Flow<List<MotelRoom>> = roomDao.getAll().map { list ->
        list.map { it.toDomain() }
    }

    val allRoomsWithTenants: Flow<List<RoomWithTenant>> = roomDao.getAllRoomsWithTenants()

    suspend fun insertRoom(room: MotelRoom) {
        roomDao.insert(room.toEntity())
    }

    suspend fun updateRoom(room: MotelRoom) {
        roomDao.update(room.toEntity())
    }

    suspend fun deleteRoom(room: MotelRoom) {
        roomDao.delete(room.toEntity())
    }

    suspend fun getRoomById(id: String): MotelRoom? {
        return roomDao.getById(id)?.toDomain()
    }
}
