package com.example.mystatisticsapp.domain.usecase

import com.example.mystatisticsapp.domain.model.User
import com.example.mystatisticsapp.domain.repository.RikmastersRepository
import com.example.mystatisticsapp.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
     private val repository: RikmastersRepository
) {
     operator fun invoke(): Flow<Result<List<User>>> {
          return repository.getUsers()
     }
}