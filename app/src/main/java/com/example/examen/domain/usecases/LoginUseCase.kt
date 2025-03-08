package com.example.examen.domain.usecases

import com.example.examen.data.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend operator fun invoke(username: String, password: String) =
        loginRepository.login(username, password)
}