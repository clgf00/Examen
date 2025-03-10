package com.example.examen.ui.pantallaInformes

import com.example.examen.data.local.model.Informe

data class InformesState (
    val informes: List<Informe> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null)
