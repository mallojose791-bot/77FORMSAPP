package com.example.a77formsapp.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a77formsapp.data.repository.AuthRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState> = _state.asStateFlow()

    // Fires once when login succeeds.
    private val _loggedIn = Channel<Unit>(Channel.BUFFERED)
    val loggedIn = _loggedIn.receiveAsFlow()

    fun onEmailChange(value: String) {
        _state.update { it.copy(email = value, error = null) }
    }

    fun onPasswordChange(value: String) {
        _state.update { it.copy(password = value, error = null) }
    }

    fun login() {
        val current = _state.value
        if (current.isLoading) return

        if (current.email.isBlank() || current.password.isBlank()) {
            _state.update { it.copy(error = "Enter your email and password.") }
            return
        }

        _state.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            repository.login(current.email.trim(), current.password).fold(
                onSuccess = {
                    // The password is not kept after a successful login.
                    _state.update { s -> s.copy(isLoading = false, password = "") }
                    _loggedIn.send(Unit)
                },
                onFailure = { e ->
                    _state.update { s ->
                        s.copy(isLoading = false, error = e.message ?: "Couldn't sign in.")
                    }
                }
            )
        }
    }
}