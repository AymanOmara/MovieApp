package com.example.data.network.utils

import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkUtils @Inject constructor() {

    fun <T> safeApiCall(apiCall: suspend () -> T): Flow<Result<T>> = flow {
        emit(Result.Loading)
        try {
            val result = apiCall()
            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e))
        }
    }
}
