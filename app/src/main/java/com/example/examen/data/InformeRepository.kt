package com.example.examen.data

import com.example.examen.data.local.InformeDao
import com.example.examen.data.local.toInforme
import com.example.examen.data.local.toInformeEntity
import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.local.model.Informe
import com.example.examen.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class InformeRepository @Inject constructor(
    private val informeDao: InformeDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) {
    fun getInformes(): Flow<NetworkResult<List<Informe>>> = flow {
        try {
            val informes = informeDao.getAll().map { it.toInforme() }
            emit(NetworkResult.Success(informes))
        } catch (e: Exception) {
            emit(NetworkResult.Error("Error al obtener informes: ${e.message}"))
        }
    }.flowOn(dispatcher)

    suspend fun insertInforme(informe: Informe) {
        informeDao.insertInforme(informe.toInformeEntity())
    }

    suspend fun getInformeById(informeId: Int): Informe? {
        val informeEntity = informeDao.getInformeById(informeId)
        return informeEntity?.toInforme()
    }

    suspend fun updateInforme(informe: Informe) {
        informeDao.updateInforme(informe.toInformeEntity())
    }
}