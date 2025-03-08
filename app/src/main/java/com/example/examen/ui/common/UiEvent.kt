package com.example.examen.ui.common

sealed class UiEvent {
    data object Navigate : UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
}