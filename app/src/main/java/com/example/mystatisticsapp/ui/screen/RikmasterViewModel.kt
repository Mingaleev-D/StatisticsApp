package com.example.mystatisticsapp.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystatisticsapp.data.model.AgeDistributionData
import com.example.mystatisticsapp.data.model.ChartDataPoint
import com.example.mystatisticsapp.data.model.DonutChartSegment
import com.example.mystatisticsapp.domain.model.StatisticEntry
import com.example.mystatisticsapp.domain.model.User
import com.example.mystatisticsapp.domain.usecase.GetAgeDistributionDataUseCase
import com.example.mystatisticsapp.domain.usecase.GetChartDataPointsUseCase
import com.example.mystatisticsapp.domain.usecase.GetGenderDonutSegmentsUseCase
import com.example.mystatisticsapp.domain.usecase.GetStatisticsUseCase
import com.example.mystatisticsapp.domain.usecase.GetUsersUseCase
import com.example.mystatisticsapp.utils.Result
import com.example.mystatisticsapp.utils.mapResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RikmasterViewModel @Inject constructor(
     private val getStatisticsUseCase: GetStatisticsUseCase,
     private val getUsersUseCase: GetUsersUseCase,
     private val getChartDataPointsUseCase: GetChartDataPointsUseCase,
     private val getGenderDonutSegmentsUseCase: GetGenderDonutSegmentsUseCase,
     private val getAgeDistributionDataUseCase: GetAgeDistributionDataUseCase
) : ViewModel() {
     private val _usersState = MutableStateFlow<Result<List<User>>>(Result.Loading)
     val usersState: StateFlow<Result<List<User>>> = _usersState.asStateFlow()
     private val _rawStatisticsState =
          MutableStateFlow<Result<List<StatisticEntry>>>(Result.Loading)

     init {
          fetchUsers()
          fetchStatistics()
     }

     val chartDataState: StateFlow<Result<List<ChartDataPoint>>> =
          _rawStatisticsState.mapResult(
               viewModelScope
          ) { rawStats ->
               val allViewEvents = rawStats.filter { it.type == "view" }
               getChartDataPointsUseCase(allViewEvents)
          }

     val genderDistributionState: StateFlow<Result<List<DonutChartSegment>>> =
          usersState.mapResult(viewModelScope) { users ->
               getGenderDonutSegmentsUseCase(users)
          }

     val ageDistributionState: StateFlow<Result<List<AgeDistributionData>>> =
          usersState.mapResult(viewModelScope) { users ->
               getAgeDistributionDataUseCase(users)
          }

     private fun fetchUsers() {
          viewModelScope.launch {
               getUsersUseCase().collectLatest { result ->
                    _usersState.value = result
               }
          }
     }

     private fun fetchStatistics() {
          viewModelScope.launch {
               getStatisticsUseCase().collectLatest { result ->
                    _rawStatisticsState.value = result
               }
          }
     }
}

