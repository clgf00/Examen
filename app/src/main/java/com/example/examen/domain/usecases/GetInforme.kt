package com.example.examen.domain.usecases

import com.example.examen.data.InformeRepository
import com.example.examen.data.remote.model.Informe
import javax.inject.Inject

class GetInforme @Inject constructor(
    private val repository: InformeRepository
) {
    suspend operator fun invoke(informeId: Int): Informe? {
        return repository.getInformeById(informeId)
    }
}
