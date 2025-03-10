package com.example.examen.data.remote.utils


import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val token = runBlocking { tokenManager.token.first() }
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

