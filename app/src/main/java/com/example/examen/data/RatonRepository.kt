package com.example.examen.data

import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.datasource.BaseApiResponse
import com.example.examen.data.remote.model.Raton
import com.example.examen.data.remote.services.RatonService
import com.example.examen.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RatonRepository @Inject constructor(
    private val ratonService: RatonService,
    @IoDispatcher private val dispatcher: CoroutineDispatcher

) : BaseApiResponse() {
    suspend fun getRatones() = withContext(dispatcher) {
        try {
            safeApiCall { ratonService.getRatones() }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    suspend fun addRaton(raton:Raton) = withContext(dispatcher){
        try {
            safeApiCall { ratonService.addRaton(raton) }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}