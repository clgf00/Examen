package com.example.examen.ui.pantallaDetalleAlumnos

sealed class DetailAlumnoEvent {
    data object AvisoVisto : DetailAlumnoEvent()
    data class GetAlumno(val name : String) : DetailAlumnoEvent()
}