package com.example.examen.data.remote.utils


import com.example.examen.data.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class AuthAuthenticator @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        return runBlocking {
            val token = preferencesRepository.token.first()
            if (token == null) {
                preferencesRepository.clearTokens()
                null
            } else {
                response.request.newBuilder()
                    .header("Authorization", "Bearer $token")
                    .build()
            }
        }
    }
}