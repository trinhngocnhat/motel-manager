package com.example.managemotel.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemotel.data.AppDatabase
import com.example.managemotel.data.repository.OwnerRepository
import com.example.managemotel.models.*
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OwnerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: OwnerRepository

    val managedUsers: StateFlow<List<User>>
    val allTenants: StateFlow<List<User>>
    val allRooms: StateFlow<List<MotelRoom>>
    val allContracts: StateFlow<List<RentalContract>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = OwnerRepository(db.userDao(), db.roomManagementDao())
        managedUsers = repository.managedUsers.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        allTenants = repository.tenants.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        allRooms = repository.allRooms.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
        allContracts = repository.allContracts.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    }

    fun addUser(user: User) = viewModelScope.launch { repository.insertUser(user) }
    fun deleteUser(user: User) = viewModelScope.launch { repository.deleteUser(user) }

    fun updateRoomType(room: MotelRoom, newTypeId: String) = viewModelScope.launch {
        repository.updateRoom(room.copy(typeRooms = newTypeId))
    }

    fun addTenantToContract(contractId: String, userId: String) = viewModelScope.launch {
        repository.addTenantToContract(contractId, userId)
    }

    fun getTenantsForContract(contractId: String) = repository.getTenantsForContract(contractId)
}
