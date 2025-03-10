package com.example.examen.data.remote.services

import com.example.examen.common.Constantes
import com.example.examen.data.remote.model.Token
import com.example.examen.data.remote.model.LoginUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST(Constantes.RUTA_LOGIN)
    suspend fun login(@Body loginUser: LoginUser): Response<Token>
}