package com.example.managemotel.repository

import com.example.managemotel.local.repository.LocalRepository
import com.example.managemotel.models.MotelRoom
import com.example.managemotel.network.ApiService
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val apiService: ApiService,
    private val localRepository: LocalRepository
) {
    // Single Source of Truth: UI observes local database
    val allRooms: Flow<List<MotelRoom>> = localRepository.getAllRooms()

    suspend fun refreshRooms() {
        try {
            val response = apiService.getAllRooms()
            if (response.isSuccessful) {
                response.body()?.let { remoteRooms ->
                    localRepository.saveRooms(remoteRooms)
                }
            }
        } catch (e: Exception) {
            // Handle error (e.g., log it or show a message)
        }
    }

    // Additional methods for other entities would follow the same pattern
}
