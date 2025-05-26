package com.example.mystatisticsapp.domain.usecase

import com.example.mystatisticsapp.domain.model.StatisticEntry
import com.example.mystatisticsapp.domain.repository.RikmastersRepository
import com.example.mystatisticsapp.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStatisticsUseCase @Inject constructor(
     private val repository: RikmastersRepository
) {
     operator fun invoke(): Flow<Result<List<StatisticEntry>>> {
          return repository.getStatistics()
     }
}