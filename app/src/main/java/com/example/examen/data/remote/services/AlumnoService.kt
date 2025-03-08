package com.example.examen.data.remote.services

import com.example.examen.data.remote.model.Alumno
import retrofit2.Response
import retrofit2.http.GET

fun interface AlumnoService {
    @GET("/alumnos")
    suspend fun getAlumnos() : Response<List<Alumno>>
}