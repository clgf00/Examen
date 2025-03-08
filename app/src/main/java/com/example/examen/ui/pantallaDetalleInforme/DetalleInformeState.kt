package com.example.examen.ui.pantallaDetalleInforme

import com.example.examen.data.remote.model.Informe

data class DetalleInformeState(
    val informe: Informe? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
