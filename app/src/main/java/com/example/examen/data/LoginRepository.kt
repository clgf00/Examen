package com.example.examen.data

import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.datasource.BaseApiResponse
import com.example.examen.data.remote.services.LoginService
import com.example.examen.di.IoDispatcher
import com.example.examen.data.remote.model.LoginUser
import com.example.examen.data.remote.utils.TokenManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val loginService: LoginService,
    private val tokenManager: TokenManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : BaseApiResponse() {
    suspend fun login(username: String, password: String) = withContext(dispatcher) {
        val result = safeApiCall { loginService.login(LoginUser(username, password)) }
        if (result is NetworkResult.Success) {
            tokenManager.saveTokens(result.data.accessToken)
        }
        result
    }
}