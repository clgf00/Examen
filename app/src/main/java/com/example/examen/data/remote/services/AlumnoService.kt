package com.example.examen.data.remote.services

import com.example.examen.common.Constantes
import com.example.examen.data.remote.model.Alumno
import retrofit2.Response
import retrofit2.http.GET

fun interface AlumnoService {
    @GET(Constantes.RUTA_ALUMNOS)
    suspend fun getAlumnos() : Response<List<Alumno>>
}