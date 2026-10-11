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

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun onPageStarted() {
        _isLoading.value = true
    }

    fun onPageFinished() {
        _isLoading.value = false
    }
}