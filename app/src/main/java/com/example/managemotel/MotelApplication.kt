package com.example.managemotel

import android.app.Application
import com.example.managemotel.data.AppDatabase

class MotelApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }
}
