package com.example.mystatisticsapp.data.model


import com.google.gson.annotations.SerializedName
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

data class StatisticksDto(
    @SerializedName("statistics")
    val statisticDtos: List<StatisticDto?>?
)

// Модель для данных графика (дата и количество посетителей)
data class ChartDataPoint(
    val date: LocalDate,
    val count: Int
)

