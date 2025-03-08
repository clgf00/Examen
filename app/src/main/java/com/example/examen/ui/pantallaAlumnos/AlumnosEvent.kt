package com.example.examen.ui.pantallaAlumnos

sealed class AlumnosEvent {
    data object AvisoVisto : AlumnosEvent()
    data class AlumnoSelected (val name : String) : AlumnosEvent()
    data object GetAlumnos : AlumnosEvent()
}