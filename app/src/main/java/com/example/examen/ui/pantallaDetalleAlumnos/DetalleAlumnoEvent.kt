package com.example.examen.ui.pantallaDetalleAlumnos

sealed class DetalleAlumnoEvent {
    data object AvisoVisto : DetalleAlumnoEvent()
    data class GetAlumno(val name : String) : DetalleAlumnoEvent()
}