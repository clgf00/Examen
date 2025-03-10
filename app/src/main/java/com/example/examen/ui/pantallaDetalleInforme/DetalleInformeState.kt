package com.example.examen.ui.pantallaDetalleInforme

import com.example.examen.data.local.model.Informe

data class DetalleInformeState(
    val informe: Informe? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
