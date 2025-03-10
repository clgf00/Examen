package com.example.examen.ui.pantallaRatones

import com.example.examen.data.remote.model.Raton
import com.example.examen.ui.common.UiEvent

data class RatonState(
    val aviso : UiEvent ?= null,
    val isLoading : Boolean = false,
    val raton : List<Raton> = emptyList(),
)
