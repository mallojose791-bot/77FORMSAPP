package com.example.a77formsapp.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val id: Int,
    val displayName: String? = null,
    val roles: List<String>? = null,
    val mustChangePassword: Boolean = false
)