package com.riko.nit3213.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riko.nit3213.network.Entity
import com.riko.nit3213.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class DashboardUiState {
    object Loading : DashboardUiState()
    data class Success(val entities: List<Entity>) : DashboardUiState()
    data class Error(val message: String) : DashboardUiState()
}

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: AppRepository
) : ViewModel() {

    private val _uiState = MutableLiveData<DashboardUiState>()
    val uiState: LiveData<DashboardUiState> = _uiState

    fun loadDashboard(keypass: String) {
        _uiState.value = DashboardUiState.Loading

        viewModelScope.launch {
            try {
                val response = repository.getDashboard(keypass)
                _uiState.value = DashboardUiState.Success(response.entities)
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(
                    e.message ?: "Failed to load dashboard."
                )
            }
        }
    }
}