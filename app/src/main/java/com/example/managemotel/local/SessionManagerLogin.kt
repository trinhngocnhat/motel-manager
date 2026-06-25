package com.example.managemotel.local

import android.content.Context

class SessionManager(context: Context) {

    private val prefs =
        context.getSharedPreferences(
            "motel_session",
            Context.MODE_PRIVATE
        )

    fun saveLogin(
        UserID: String,
        Email: String,
        Role: String
    ) {
        prefs.edit()
            .putBoolean("is_logged_in", true)
            .putString("user_id", UserID)
            .putString("Email", Email)
            .putString("Role", Role)
            .apply()
    }

    fun getUserId(): String? {
        return prefs.getString("user_id", null)
    }

    fun getEmail(): String? {
        return prefs.getString("Email", null)
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(
            "is_logged_in",
            false
        )
    }

    fun getRole(): String? {
        return prefs.getString(
            "Role",
            null
        )
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}