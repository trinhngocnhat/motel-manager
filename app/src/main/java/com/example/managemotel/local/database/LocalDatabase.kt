package com.example.managemotel.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.managemotel.local.dao.*
import com.example.managemotel.local.entity.*

@Database(
    entities = [
        UserEntity::class,
        RoomTypeEntity::class,
        RoomEntity::class,
        RentalContractEntity::class,
        ContractTenantEntity::class,
        MaintenanceHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    abstract fun rentalContractDao(): RentalContractDao
    abstract fun maintenanceHistoryDao(): MaintenanceHistoryDao
}

object DatabaseProvider {
    @Volatile
    private var INSTANCE: LocalDatabase? = null

    fun getDatabase(context: Context): LocalDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java,
                "motel_manager_local_cache"
            )
            .fallbackToDestructiveMigration()
            .build()
            INSTANCE = instance
            instance
        }
    }
}
