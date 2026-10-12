package com.example.a77formsapp.ui.webview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// title and url arrive through the navigation arguments (SavedStateHandle).
class FormWebViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    val title: String = savedStateHandle.get<String>("title").orEmpty()
    val url: String = savedStateHandle.get<String>("url").orEmpty()

    // Only open normal web links (blocks things like javascript: or file: URLs coming from the API).
    val isUrlSupported: Boolean =
        url.startsWith("https://", ignoreCase = true) || url.startsWith("http://", ignoreCase = true)

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun onPageStarted() {
        _isLoading.value = true
        _errorMessage.value = null
    }

    fun onPageFinished() {
        _isLoading.value = false
    }

    fun onError(message: String?) {
        _errorMessage.value = message ?: "Couldn't load this page."
    }
}