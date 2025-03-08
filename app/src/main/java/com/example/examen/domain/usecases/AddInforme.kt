package com.example.examen.domain.usecases

import com.example.examen.data.InformeRepository
import com.example.examen.data.remote.model.Informe
import javax.inject.Inject

class AddInforme @Inject constructor(
    private val repository: InformeRepository
) {
    suspend operator fun invoke(informe: Informe) {
        repository.insertInforme(informe)
    }
}