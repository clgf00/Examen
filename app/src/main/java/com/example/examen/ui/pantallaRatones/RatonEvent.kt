package com.example.examen.ui.pantallaRatones

sealed class RatonEvent {
    data class AddRaton (val name : String) : RatonEvent()
    data object AvisoVisto: RatonEvent()
    data object GetRatones : RatonEvent()
}