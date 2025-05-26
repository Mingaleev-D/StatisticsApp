package com.example.mystatisticsapp.domain.model

import java.time.LocalDate

data class StatisticEntry(
     val userId: Int,
     val type: String,
     val date: LocalDate // Дата уже в удобном формате
)
