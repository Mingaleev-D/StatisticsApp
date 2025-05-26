package com.example.mystatisticsapp.domain.repository

import com.example.mystatisticsapp.domain.model.StatisticEntry
import com.example.mystatisticsapp.domain.model.User
import com.example.mystatisticsapp.utils.Result
import kotlinx.coroutines.flow.Flow

interface RikmastersRepository {
     fun getUsers(): Flow<Result<List<User>>>
     fun getStatistics(): Flow<Result<List<StatisticEntry>>>
}