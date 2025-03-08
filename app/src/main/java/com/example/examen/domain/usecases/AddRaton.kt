package com.example.examen.domain.usecases

import com.example.examen.data.RatonRepository
import com.example.examen.data.remote.model.Raton
import javax.inject.Inject

class AddRaton @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke(raton:Raton) =
        ratonRepository.addRat(raton)
}