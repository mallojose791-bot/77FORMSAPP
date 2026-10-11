package com.example.a77formsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a77formsapp.data.repository.FormsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FormsRepository = FormsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadForms()
    }

    fun loadForms() {
        _uiState.value = HomeUiState.Loading
        viewModelScope.launch {
            repository.getForms().fold(
                onSuccess = { _uiState.value = HomeUiState.Success(it) },
                onFailure = { _uiState.value = HomeUiState.Error(it.message ?: "Something went wrong") }
            )
        }
    }
}