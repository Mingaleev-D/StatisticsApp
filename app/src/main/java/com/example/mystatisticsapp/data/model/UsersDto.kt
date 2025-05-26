package com.example.mystatisticsapp.data.model


import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName

data class UsersDto(
    @SerializedName("users")
    val userDtos: List<UserDto?>?
)

// Модель для сегмента диаграммы
data class DonutChartSegment(
    val label: String,
    val value: Float,
    val color: Color
)

data class GenderDistributionData(
    val maleCount: Int,
    val femaleCount: Int,
    val otherCount: Int
) {
    val total: Int
        get() = maleCount + femaleCount + otherCount
}

// Данные о распределении по полу для одной возрастной группы
// Представляет одну возрастную группу и количество пользователей в ней по двум метрикам
data class AgeDistributionData(
    val label: String,
    val countPrimary: Int,
    val countSecondary: Int,
    val minAge: Int?,
    val maxAge: Int?
) {
    val totalGroupCount: Int get() = countPrimary + countSecondary
}

