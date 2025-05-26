package com.example.mystatisticsapp.domain.usecase

import com.example.mystatisticsapp.data.model.ChartDataPoint
import com.example.mystatisticsapp.domain.model.StatisticEntry
import javax.inject.Inject

class GetChartDataPointsUseCase @Inject constructor() {
     operator fun invoke(viewEvents: List<StatisticEntry>): List<ChartDataPoint> {
          return viewEvents
               .groupingBy { it.date }
               .eachCount()
               .map { (date, count) -> ChartDataPoint(date, count) }
               .sortedBy { it.date }
     }
}