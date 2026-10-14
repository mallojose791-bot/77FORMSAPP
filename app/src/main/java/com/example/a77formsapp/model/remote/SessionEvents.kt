package com.example.a77formsapp.data.remote

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

// Lets the data layer tell the UI "the session is gone, show the sign-in screen".
object SessionEvents {
    private val _expired = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val expired: SharedFlow<Unit> = _expired.asSharedFlow()

    fun notifyExpired() {
        _expired.tryEmit(Unit)
    }
}