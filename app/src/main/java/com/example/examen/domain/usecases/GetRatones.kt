package com.example.examen.domain.usecases

import com.example.examen.data.RatonRepository
import javax.inject.Inject

class GetRatones @Inject constructor(private val ratonRepository: RatonRepository) {
    suspend operator fun invoke() =
        ratonRepository.getRatones()
}