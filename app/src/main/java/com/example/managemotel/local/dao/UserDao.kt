package com.example.managemotel.local.dao

import androidx.room.*
import com.example.managemotel.local.entity.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE userId = :userId")
    suspend fun getById(userId: String): UserEntity?

    @Query("SELECT * FROM users")
    fun getAll(): kotlinx.coroutines.flow.Flow<List<UserEntity>>

    @Query("DELETE FROM users")
    suspend fun clearAll()

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT * FROM users WHERE Email = :email AND Password = :password LIMIT 1")
    suspend fun loginLocal(email: String, password: String): UserEntity?
}
