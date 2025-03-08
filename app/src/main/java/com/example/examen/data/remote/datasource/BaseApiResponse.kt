package com.example.examen.data.remote.datasource
import com.example.examen.data.remote.NetworkResult
import com.example.examen.data.remote.utils.ApiError
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

abstract class BaseApiResponse {
    inline fun <reified T> safeApiCall(apiCall: () -> Response<T>): NetworkResult<T> {
        return try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                when {
                    body != null -> NetworkResult.Success(body)
                    else -> NetworkResult.Success(Unit as T)
                }
            } else {
                response.errorBody()?.let { errorBody ->
                    parseErrorResponse(errorBody)
                } ?: NetworkResult.Error("${response.code()} ${response.message()}")
            }
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }

    fun <T> parseErrorResponse(errorBody: ResponseBody): NetworkResult<T> {
        return try {
            val errorBodyString = errorBody.string()
            val apiError = Gson().fromJson(errorBodyString, ApiError::class.java)
            NetworkResult.Error(apiError.message)
        } catch (e: Exception) {
            NetworkResult.Error(e.message ?: e.toString())
        }
    }
}




