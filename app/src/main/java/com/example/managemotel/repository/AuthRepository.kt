package com.example.managemotel.repository

import com.example.managemotel.domain.model.User
import com.example.managemotel.local.dao.UserDao
import com.example.managemotel.local.mapper.toDomain
import com.example.managemotel.local.mapper.toEntity
import com.example.managemotel.models.LoginRequest
import com.example.managemotel.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
    private val apiService: ApiService,
    private val userDao: UserDao
) {
    suspend fun login(username: String, password: String): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = apiService.login(LoginRequest(username, password))
            if (response.isSuccessful) {
                val loginResponse = response.body()
                android.util.Log.d("AuthRepository", "Response body: $loginResponse")

                val userDto = loginResponse?.user
                if (userDto != null) {
                    val userEntity = userDto.toEntity()
                    userDao.clearAll()
                    
                    // If the backend returns password in the user object, we can save it.
                    // But your backend doesn't return it in the user object.
                    // For local login support later, we might want to store it if provided.
                    val entityWithPassword = userEntity.copy(password = password)
                    
                    userDao.insertUser(entityWithPassword)
                    Result.success(entityWithPassword.toDomain())
                } else {
                    Result.failure(Exception("Dữ liệu người dùng trống"))
                }
            } else {
                val errorBody = response.errorBody()?.string()
                val errorMessage = try {
                    val json = android.util.JsonReader(errorBody?.reader())
                    var msg = "Lỗi không xác định"
                    json.beginObject()
                    while (json.hasNext()) {
                        if (json.nextName() == "error") {
                            msg = json.nextString()
                        } else {
                            json.skipValue()
                        }
                    }
                    json.endObject()
                    msg
                } catch (e: Exception) {
                    "Lỗi hệ thống (${response.code()})"
                }
                Result.failure(Exception(errorMessage))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getLocalUser(userId: String): User? = withContext(Dispatchers.IO) {
        userDao.getById(userId)?.toDomain()
    }
}
