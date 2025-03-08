package com.example.examen.ui.pantallaRatones

sealed class RatonEvent {
    data class AddRat (val name : String) : RatonEvent()

    data object AvisoVisto: RatonEvent()
    data object GetRats : RatonEvent()
}