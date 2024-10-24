package com.kyawlinnthant.quotable.data.remote

import android.util.Log
import retrofit2.Response
import java.net.SocketTimeoutException

sealed class NetworkResult<out T>(
    open val data: T? = null,
) {
    data class Success<out T>(override val data: T) : NetworkResult<T>()

    data class Failed(val error: String) : NetworkResult<Nothing>()
}

inline fun <reified T> safeApiCall(apiCall: () -> Response<T>): NetworkResult<T> {
    try {
        val response = apiCall()
        // 2xx
        if (response.isSuccessful) {
            val body = response.body()
            return NetworkResult.Success(data = body!!)
        }
        // 4xx, 5xx
        val errorBody = response.errorBody()
        return NetworkResult.Failed(error = errorBody?.string() ?: "Something went wrong!")
    } catch (e: SocketTimeoutException) {
        return NetworkResult.Failed(error = e.localizedMessage ?: "Socket Timeout!")
    } catch (e: Exception) {
        Log.e("klt", "safeApiCall: ${e.localizedMessage}")
        return NetworkResult.Failed(error = e.localizedMessage ?: "Default Exception!")
    }
}
