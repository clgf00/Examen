package com.example.examen.ui.pantallaDetalleAlumnos

import com.example.examen.data.remote.model.Alumno
import com.example.examen.ui.common.UiEvent

data class DetalleAlumnoState(
    val alumno: Alumno = Alumno(),
    val isLoading: Boolean = false,
    val aviso: UiEvent? = null
)
