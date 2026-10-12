package com.example.a77formsapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a77formsapp.data.model.FormItem
import com.example.a77formsapp.data.repository.FormsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FormsRepository = FormsRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    // One-time messages (shown as a snackbar).
    private val _messages = Channel<String>(Channel.BUFFERED)
    val messages = _messages.receiveAsFlow()

    // The list shown on screen: all forms filtered by the search text (matches titles).
    val visibleForms: StateFlow<List<FormItem>> =
        combine(_uiState, _query) { state, query ->
            val forms = (state as? HomeUiState.Success)?.forms.orEmpty()
            val needle = query.trim()
            if (needle.isEmpty()) {
                forms
            } else {
                forms.filter { it.title.contains(needle, ignoreCase = true) }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptyList())

    init {
        loadForms()
    }

    fun onQueryChange(value: String) {
        _query.value = value
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

    // Pull-to-refresh: keeps the current list on screen and only shows a message if it fails.
    fun refresh() {
        if (_isRefreshing.value) return
        viewModelScope.launch {
            _isRefreshing.value = true
            repository.getForms().fold(
                onSuccess = { _uiState.value = HomeUiState.Success(it) },
                onFailure = { _messages.send("Couldn't refresh. Check your connection.") }
            )
            _isRefreshing.value = false
        }
    }
}