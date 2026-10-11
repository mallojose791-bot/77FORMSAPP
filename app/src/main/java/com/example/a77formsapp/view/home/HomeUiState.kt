package com.example.a77formsapp.ui.home

import com.example.a77formsapp.data.model.FormItem

sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(val forms: List<FormItem>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}