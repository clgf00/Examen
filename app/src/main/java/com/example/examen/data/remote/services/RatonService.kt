package com.example.examen.data.remote.services

import com.example.examen.common.Constantes
import com.example.examen.data.remote.model.Raton
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RatonService {
    @GET(Constantes.RUTA_RATONES)
    suspend fun getRatones() : Response<List<Raton>>
    @POST(Constantes.RUTA_RATONES)
    suspend fun addRaton (@Body raton: Raton) : Response<Raton>
}