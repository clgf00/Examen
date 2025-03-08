package com.example.examen.ui.pantallaAlumnos

import com.example.examen.data.remote.model.Alumno
import com.example.examen.ui.common.UiEvent

data class AlumnosState (
    val aviso : UiEvent?=null,
    val isLoading : Boolean = false,
    val alumnos : List<Alumno> = emptyList(),
    val selectedName : String = ""
)