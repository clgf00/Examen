package com.example.examen.data

import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.datasource.BaseApiResponse
import com.example.examen.data.remote.services.AlumnoService
import com.example.examen.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlumnoRepository @Inject constructor(
    private val alumnoService: AlumnoService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getPlayers() = withContext(dispatcher) {
        try {
            safeApiCall { alumnoService.getAlumnos() }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}