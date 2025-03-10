package com.example.examen.domain.usecases

import com.example.examen.data.InformeRepository
import com.example.examen.data.local.model.Informe
import javax.inject.Inject

class UpdateInforme @Inject constructor(
    private val informeRepository: InformeRepository
) {
    suspend operator fun invoke(informe: Informe) =
        informeRepository.updateInforme(informe)
}