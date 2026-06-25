package com.example.managemotel.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.managemotel.data.AppDatabase
import com.example.managemotel.domain.model.LoginUiState
import com.example.managemotel.network.RetrofitClient
import com.example.managemotel.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: AuthRepository

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AuthRepository(RetrofitClient.instance, database.userDao())
    }

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading
            val result = repository.login(username, password)
            result.onSuccess { user ->
                _uiState.value = LoginUiState.Success(user)
            }.onFailure { exception ->
                _uiState.value = LoginUiState.Error(exception.message ?: "Đã xảy ra lỗi")
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}
