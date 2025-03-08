package com.example.examen.domain.usecases

import com.example.examen.data.AlumnoRepository
import javax.inject.Inject

class GetAlumnos @Inject constructor(private val alumnoRepository: AlumnoRepository) {
    suspend operator fun invoke() =
        alumnoRepository.getPlayers()
}