    package com.example.examen.data.remote.utils


    import com.example.examen.common.TokenExpiredException
    import kotlinx.coroutines.flow.first
    import kotlinx.coroutines.runBlocking
    import okhttp3.Authenticator
    import okhttp3.Request
    import okhttp3.Response
    import okhttp3.Route
    import javax.inject.Inject


    class AuthAuthenticator @Inject constructor(
        private val tokenManager: TokenManager
    ) : Authenticator {
        override fun authenticate(route: Route?, response: Response): Request? {
            return runBlocking {
                val token = tokenManager.token.first()
                if (token == null) {
                    tokenManager.deleteTokens()
                    throw TokenExpiredException()
                } else {
                    response.request.newBuilder()
                        .header("Authorization", "Bearer $token")
                        .build()
                }
            }
        }
    }
