package com.example.managemotel.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemotel.data.AppDatabase
import com.example.managemotel.data.repository.RoomRepository
import com.example.managemotel.local.mapper.toEntity
import com.example.managemotel.models.MotelRoom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RoomViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: RoomRepository
    private val userDao = AppDatabase.getDatabase(application).userDao()
    val allRooms: Flow<List<MotelRoom>>
    val allRoomsWithTenants: Flow<List<com.example.managemotel.models.RoomWithTenant>>

    init {
        val roomDao = AppDatabase.getDatabase(application).roomDao()
        repository = RoomRepository(roomDao)
        allRooms = repository.allRooms
        allRoomsWithTenants = repository.allRoomsWithTenants
    }

    suspend fun loginLocal(email: String, pass: String) = userDao.loginLocal(email, pass)
    
    fun insertUser(user: com.example.managemotel.models.User) = viewModelScope.launch {
        userDao.insertUser(user.toEntity())
    }

    fun insert(room: MotelRoom) = viewModelScope.launch {
        repository.insertRoom(room)
    }

    fun update(room: MotelRoom) = viewModelScope.launch {
        repository.updateRoom(room)
    }

    fun updatePaymentStatus(roomId: String, status: String) = viewModelScope.launch {
        val room = repository.getRoomById(roomId)
        if (room != null) {
            repository.updateRoom(room.copy(paymentStatus = status))
        }
    }

    fun delete(room: MotelRoom) = viewModelScope.launch {
        repository.deleteRoom(room)
    }

    suspend fun getRoomById(roomId: String): MotelRoom? {
        return repository.getRoomById(roomId)
    }

    fun syncRoomsFromBackend() = viewModelScope.launch {
        try {
            val response = com.example.managemotel.network.RetrofitClient.instance.getAllRooms()
            if (response.isSuccessful) {
                response.body()?.let { rooms ->
                    for (room in rooms) {
                        repository.insertRoom(room)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
