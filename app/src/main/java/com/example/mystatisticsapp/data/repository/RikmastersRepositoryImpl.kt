package com.example.mystatisticsapp.data.repository

import com.example.mystatisticsapp.data.mappers.toDomain
import com.example.mystatisticsapp.data.mappers.toDomainList
import com.example.mystatisticsapp.data.remote.ApiService
import com.example.mystatisticsapp.domain.model.StatisticEntry
import com.example.mystatisticsapp.domain.model.User
import com.example.mystatisticsapp.domain.repository.RikmastersRepository
import com.example.mystatisticsapp.utils.Result
import com.example.mystatisticsapp.utils.safeFlow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class RikmastersRepositoryImpl @Inject constructor(
     private val apiService: ApiService
) : RikmastersRepository {
     override fun getUsers(): Flow<Result<List<User>>> = safeFlow {
          apiService.getUsers().userDtos?.mapNotNull { it?.toDomain() } ?: emptyList()
     }

     override fun getStatistics(): Flow<Result<List<StatisticEntry>>> = safeFlow {
          apiService.getStatistics().toDomainList()
     }
}