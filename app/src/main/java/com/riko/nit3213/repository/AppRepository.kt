package com.riko.nit3213.repository

import com.riko.nit3213.network.ApiService
import com.riko.nit3213.network.DashboardResponse
import com.riko.nit3213.network.LoginResponse
import com.riko.nit3213.network.LoginRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun login(username: String, password: String): LoginResponse {
        return apiService.login(LoginRequest(username, password))
    }

    suspend fun getDashboard(keypass: String): DashboardResponse {
        return apiService.getDashboard(keypass)
    }
}