package com.example.managemotel.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemotel.data.AppDatabase
import com.example.managemotel.data.repository.RoomRepository
import com.example.managemotel.models.Room
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RoomRepository
    val allRooms: Flow<List<Room>>

    init {
        val roomDao = AppDatabase.getDatabase(application).roomDao()
        repository = RoomRepository(roomDao)
        allRooms = repository.allRooms
    }

    fun insert(room: Room) = viewModelScope.launch {
        repository.insertRoom(room)
    }

    fun update(room: Room) = viewModelScope.launch {
        repository.updateRoom(room)
    }

    fun delete(room: Room) = viewModelScope.launch {
        repository.deleteRoom(room)
    }

    suspend fun getRoomById(roomId: String): Room? {
        return repository.getRoomById(roomId)
    }
}
