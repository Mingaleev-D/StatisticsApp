package com.example.mystatisticsapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mystatisticsapp.R
import com.example.mystatisticsapp.ui.screen.components.AgeDistributionChart
import com.example.mystatisticsapp.ui.screen.components.GenderDonutChart
import com.example.mystatisticsapp.ui.screen.components.HeadingItem
import com.example.mystatisticsapp.ui.screen.components.ObserversCard
import com.example.mystatisticsapp.ui.screen.components.TimePeriod
import com.example.mystatisticsapp.ui.screen.components.TimePeriodSelector
import com.example.mystatisticsapp.ui.screen.components.TimeRange
import com.example.mystatisticsapp.ui.screen.components.TimeRangeSelector
import com.example.mystatisticsapp.ui.screen.components.UserListItem
import com.example.mystatisticsapp.ui.screen.components.VisitorLineChart
import com.example.mystatisticsapp.ui.screen.components.VisitorsCard
import com.example.mystatisticsapp.ui.screen.components.VisitorsOverviewCard
import com.example.mystatisticsapp.ui.theme.ScaffoldBackground
import com.example.mystatisticsapp.utils.Result

/*
  var selectedPeriod by remember { mutableStateOf(TimePeriod.DAILY) }
       item {
                    TimePeriodSelector(
                         selectedPeriod = selectedPeriod,
                         onPeriodSelected = { newPeriod ->
                              selectedPeriod = newPeriod
                         }
                    )
               }
 */

@Composable
fun RikmasterScreen(
     viewModel: RikmasterViewModel = hiltViewModel()
) {
     var selectedPeriod by remember { mutableStateOf(TimePeriod.DAILY) }
     var selectedRange by remember { mutableStateOf(TimeRange.TODAY) }
     val chartDataResult by viewModel.chartDataState.collectAsState()
     val usersListResult by viewModel.usersState.collectAsState()
     val genderDistributionResult by viewModel.genderDistributionState.collectAsState()
     val ageDistributionResult by viewModel.ageDistributionState.collectAsState()

     Scaffold(
          modifier = Modifier
               .fillMaxSize()
               .background(color = ScaffoldBackground),
          containerColor = MaterialTheme.colorScheme.surface,
     ) { paddingValues ->
          LazyColumn(
               modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
               contentPadding = PaddingValues(start = 16.dp)
          ) {
               // header statistics
               item {
                    Spacer(modifier = Modifier.height(42.dp))
                    HeadingItem(
                         txt = stringResource(R.string.statistics),
                         fSize = 32.sp,
                         fontWeight = FontWeight.ExtraBold,
                    )
               }
               // header Users
               item {
                    Spacer(modifier = Modifier.height(32.dp))
                    VisitorsOverviewCard()
               }

               item {
                    Spacer(modifier = Modifier.height(32.dp))
                    TimePeriodSelector(
                         selectedPeriod = selectedPeriod,
                         onPeriodSelected = { newPeriod ->
                              selectedPeriod = newPeriod
                         }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
               }

               item {
                    when (chartDataResult) {
                         is Result.Loading -> {
                              Column(modifier = Modifier.padding(16.dp)) {
                                   CircularProgressIndicator()
                                   Text(stringResource(R.string.loading_data))
                              }
                         }

                         is Result.Error -> {
                              Text(
                                   stringResource(R.string.error_txt) + (chartDataResult as Result.Error).message,
                                   modifier = Modifier.padding(16.dp)
                              )
                         }

                         is Result.Success -> {
                              VisitorLineChart(dataPoints = (chartDataResult as Result.Success).data)
                         }
                    }
               }

               item { Spacer(modifier = Modifier.height(32.dp)) }
               item {
                    HeadingItem(
                         txt = stringResource(R.string.profile_most_often),
                         fSize = 20.sp,
                         fontWeight = FontWeight.ExtraBold,
                    )
               }
               item { Spacer(modifier = Modifier.height(16.dp)) }

               when (usersListResult) {
                    is Result.Loading -> {
                         item {
                              Column(modifier = Modifier.padding(16.dp)) {
                                   CircularProgressIndicator()
                                   Text(stringResource(R.string.loading_data))
                              }
                         }
                    }

                    is Result.Error -> {
                         item {
                              Text(
                                   stringResource(R.string.error_txt) + " ${(usersListResult as Result.Error).message}",
                                   modifier = Modifier.padding(16.dp)
                              )
                         }
                    }

                    is Result.Success -> {
                         val users = (usersListResult as Result.Success).data
                         item {
                              Column(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(end = 16.dp)
                                        .clip(RoundedCornerShape(12.dp))
                              ) {
                                   if (users.isNotEmpty()) {
                                        users.take(3).forEach { user ->
                                             UserListItem(user = user)
                                        }
                                   } else {
                                        Text(
                                             "Нет данных пользователей.",
                                             modifier = Modifier.padding(16.dp)
                                        )
                                   }
                              }
                         }
                    }
               }
               item { Spacer(modifier = Modifier.height(32.dp)) }
               item {
                    HeadingItem(
                         txt = "Пол и возраст",
                         fSize = 20.sp,
                         fontWeight = FontWeight.ExtraBold,
                    )
               }

               item {
                    TimeRangeSelector(
                         selectedRange = selectedRange,
                         onRangeSelected = { newRange ->
                              selectedRange = newRange
                         }
                    )
               }
               // --- Секция круговой диаграммы пола ---
               item {
                    when (genderDistributionResult) {
                         is Result.Loading -> {
                              Column(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                                   CircularProgressIndicator()
                                   Text(stringResource(R.string.loading_data))
                              }
                         }

                         is Result.Error -> {
                              Text(
                                   stringResource(R.string.error_txt) + " ${(genderDistributionResult as Result.Error).message}",
                                   modifier = Modifier.padding(16.dp)
                              )
                         }

                         is Result.Success -> {
                              val genderSegments = (genderDistributionResult as Result.Success).data
                              if (genderSegments.isNotEmpty()) {
                                   GenderDonutChart(segments = genderSegments)
                              } else {
                                   Text(
                                        "Нет данных по полу.",
                                        modifier = Modifier.padding(16.dp)
                                   )
                              }
                         }
                    }
               }
               // --- Секция гистограммы возраста ---
               item {
                    when (ageDistributionResult) {
                         is Result.Loading -> {
                              Column(
                                   modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                   horizontalAlignment = Alignment.CenterHorizontally
                              ) {
                                   CircularProgressIndicator()
                                   Text("Загрузка данных по возрасту...")
                              }
                         }

                         is Result.Error -> {
                              Text(
                                   "Ошибка загрузки данных по возрасту: ${(ageDistributionResult as Result.Error).message}",
                                   modifier = Modifier.padding(16.dp)
                              )
                         }

                         is Result.Success -> {
                              val ageData = (ageDistributionResult as Result.Success).data
                              // Проверяем, что список не пустой и содержит хотя бы один элемент с count > 0
                              if (ageData.isNotEmpty() && ageData.any { it.totalGroupCount > 0 }) {
                                   AgeDistributionChart(ageData = ageData)
                              } else {
                                   Text(
                                        "Нет данных по возрасту.",
                                        modifier = Modifier.padding(16.dp)
                                   )
                              }
                         }
                    }
               }

               item { Spacer(modifier = Modifier.height(32.dp)) }
               item {
                    HeadingItem(
                         txt = "Наблюдатели",
                         fSize = 20.sp,
                         fontWeight = FontWeight.ExtraBold,
                    )
               }
               item { Spacer(modifier = Modifier.height(16.dp)) }
               item {
                    ObserversCard()
               }
               item { Spacer(modifier = Modifier.height(16.dp)) }
          }
     }
}