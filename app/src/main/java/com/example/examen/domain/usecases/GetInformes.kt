package com.example.examen.domain.usecases

import com.example.examen.data.InformeRepository
import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.model.Informe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInformes @Inject constructor(
    private var repository : InformeRepository
){
    operator fun invoke(): Flow<NetworkResult<List<Informe>>> = repository.getInformes()
}