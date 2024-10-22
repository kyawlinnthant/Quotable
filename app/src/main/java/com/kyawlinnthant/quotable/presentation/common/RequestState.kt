package com.kyawlinnthant.quotable.presentation.common

sealed class RequestState<out T> {
    data object Idle : RequestState<Nothing>()

    data object Loading : RequestState<Nothing>()

    data class Success<T>(val data: T) : RequestState<T>()

    data class Error(val message: String) : RequestState<Nothing>()

    fun isLoading() = this is Loading

    fun isSuccess() = this is Success

    fun isError() = this is Error

    fun getSuccessData() = (this as Success).data

    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: TypeCastException) {
            null
        }
    }

    fun getErrorMessage() = (this as Error).message

    fun getErrorMessageOrNull(): String? {
        return try {
            (this as Error).message
        } catch (e: TypeCastException) {
            null
        }
    }
}
