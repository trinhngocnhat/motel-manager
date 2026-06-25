package com.example.managemotel.data

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
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun roomDao(): RoomDao
    abstract fun rentalContractDao(): RentalContractDao
    abstract fun maintenanceHistoryDao(): MaintenanceHistoryDao
    abstract fun contractTenantDao(): ContractTenantDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "motel_manager_db"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
