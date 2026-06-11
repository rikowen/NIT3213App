package com.riko.nit3213.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    @Json(name = "username") val username: String,
    @Json(name = "password") val password: String
)

@JsonClass(generateAdapter = true)
data class LoginResponse(
    @Json(name = "keypass") val keypass: String
)

@JsonClass(generateAdapter = true)
data class DashboardResponse(
    @Json(name = "entities") val entities: List<Entity>,
    @Json(name = "entityTotal") val entityTotal: Int
)

@JsonClass(generateAdapter = true)
data class Entity(
    @Json(name = "description") val description: String
)