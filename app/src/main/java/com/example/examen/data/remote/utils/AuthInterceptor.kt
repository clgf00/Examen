package com.example.examen.data.remote.utils


import com.example.examen.data.PreferencesRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val preferencesRepository: PreferencesRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { preferencesRepository.token.first() }
        val requestWithToken = originalRequest.newBuilder()
            .apply {
                token?.let {
                    header(
                        "Authorization",
                        "Bearer $it"
                    )
                }
            }
            .build()
        return chain.proceed(requestWithToken)
    }
}

