package com.example.examen.data.remote.model


data class Alumno(
val nombre : String = "",
val apellido : String = "",
val dni : String = "",
val email : String = "",
val asignaturas : List<Asignatura> = emptyList()
)