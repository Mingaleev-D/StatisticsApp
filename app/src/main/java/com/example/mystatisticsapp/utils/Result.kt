package com.example.mystatisticsapp.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import okio.IOException
import retrofit2.HttpException

//  вспомогательный класс Result,
sealed class Result<out T> {
     object Loading : Result<Nothing>()
     data class Success<out T>(val data: T) : Result<T>()
     data class Error(val message: String) : Result<Nothing>()
}

// Вспомогательная функция для обработки Flow с ошибками
fun <T> safeFlow(call: suspend () -> T): Flow<Result<T>> = flow {
     emit(Result.Loading)
     try {
          val data = call()
          emit(Result.Success(data))
     } catch (e: HttpException) {
          val errorMessage = e.response()?.errorBody()?.string() ?: e.message()
          emit(Result.Error("HTTP Error: ${e.code()} - $errorMessage"))
     } catch (e: IOException) {
          emit(Result.Error("Network Error: No Internet connection or timeout."))
     } catch (e: Exception) {
          emit(Result.Error("An unexpected error occurred: ${e.message}"))
     }
}.flowOn(Dispatchers.IO)


// Универсальная функция для обработки Result с передачей CoroutineScope
fun <T, R> StateFlow<Result<T>>.mapResult(
     scope: CoroutineScope,
     transform: (T) -> R
): StateFlow<Result<R>> {
     return this.map { result ->
          when (result) {
               is Result.Loading -> Result.Loading
               is Result.Error -> Result.Error(result.message)
               is Result.Success -> Result.Success(transform(result.data))
          }
     }.stateIn(
          scope,
          SharingStarted.WhileSubscribed(5000),
          Result.Loading
     )
}