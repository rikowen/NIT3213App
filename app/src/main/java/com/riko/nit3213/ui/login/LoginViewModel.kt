package com.riko.nit3213.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riko.nit3213.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    data class Success(val keypass: String) : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val uiState: LiveData<LoginUiState> = _uiState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error("Username and password cannot be empty")
            return
        }

        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            try {
                val response = repository.login(username, password)
                _uiState.value = LoginUiState.Success(response.keypass)
            } catch (e: Exception) {
                _uiState.value = LoginUiState.Error(
                    e.message ?: "Login failed. Check your credentials."
                )
            }
        }
    }

    fun resetState() {
        _uiState.value = LoginUiState.Idle
    }
}