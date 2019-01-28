package com.bigscreen.mangindo.network

import retrofit2.HttpException
import java.io.IOException

class NetworkError(private val throwable: Throwable) {

    companion object {
        private const val ERROR_UNKNOWN = 0
        private const val ERROR_SERVER = 1
        private const val ERROR_CONNECTION = 2
        private const val MESSAGE_ERROR_SERVER = "Server error. Please try again."
        private const val MESSAGE_ERROR_CONNECTION = "Connection error. Please Try again."
        const val MESSAGE_ERROR = "Something went wrong. Please try again."
    }

    fun getErrorMessage() = when (getErrorType()) {
        ERROR_SERVER -> MESSAGE_ERROR_SERVER
        ERROR_CONNECTION -> MESSAGE_ERROR_CONNECTION
        else -> throwable.message.orEmpty()
    }

    private fun getErrorType() = when (throwable) {
        is HttpException -> ERROR_SERVER
        is IOException -> ERROR_CONNECTION
        else -> ERROR_UNKNOWN
    }
}
