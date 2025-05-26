package com.example.mystatisticsapp.data.mappers

import com.example.mystatisticsapp.data.model.AgeDistributionData
import com.example.mystatisticsapp.data.model.StatisticksDto
import com.example.mystatisticsapp.data.model.UserDto
import com.example.mystatisticsapp.domain.model.StatisticEntry
import com.example.mystatisticsapp.domain.model.User
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

// Расширение для UserDto для преобразования в доменную модель User
fun UserDto.toDomain(): User {
     val avatarUrl = this.fileDtos?.firstOrNull { it?.type == "avatar" }?.url

     return User(
          id = this.id ?: 0,
          username = this.username ?: "Неизвестный пользователь",
          // Здесь sex передается как есть из DTO в доменную модель User.
          // Обработка "M"/"W" в "Мужчины"/"Женщины" произойдет в ViewModel.
          sex = this.sex ?: "неизвестно",
          age = this.age ?: 0, // Если возраст null, по умолчанию 0
          isOnline = this.isOnline ?: false,
          avatarUrl = avatarUrl
     )
}

// Функция для парсинга Int даты в LocalDate (повторно используем)
fun parseDateIntToLocalDate(dateInt: Int): LocalDate? {
     val dateString = dateInt.toString()
     val formatters = listOf(
          DateTimeFormatter.ofPattern("dMMyyyy", Locale.ROOT),
          DateTimeFormatter.ofPattern("ddMMyyyy", Locale.ROOT)
     )
     for (formatter in formatters) {
          try {
               return LocalDate.parse(dateString, formatter)
          } catch (e: DateTimeParseException) {
          }
     }
     println("Ошибка парсинга даты $dateString: Неизвестный формат")
     return null
}

// Маппер для нового StatisticsResponseDto
fun StatisticksDto.toDomainList(): List<StatisticEntry> {
     val resultList = mutableListOf<StatisticEntry>()
     this.statisticDtos?.forEach { entryDto ->
          if (entryDto != null) {
               entryDto.dates?.forEach { dateInt ->
                    if (dateInt != null) {
                         val date = parseDateIntToLocalDate(dateInt)
                         if (date != null) {
                              resultList.add(
                                   StatisticEntry(
                                        userId = entryDto.userId ?: 0, // Значение по умолчанию
                                        type = entryDto.type ?: "unknown", // Значение по умолчанию
                                        date = date
                                   )
                              )
                         }
                    }
               }
          }
     }
     return resultList
}

// Константы для диапазонов возрастов (можно также вынести в отдельный файл Constants.kt)
val AGE_GROUP_DEFINITIONS = listOf(
     AgeDistributionData("18-21", 0, 0, 18, 21),
     AgeDistributionData("22-25", 0, 0, 22, 25),
     AgeDistributionData("26-30", 0, 0, 26, 30),
     AgeDistributionData("31-35", 0, 0, 31, 35),
     AgeDistributionData("36-40", 0, 0, 36, 40),
     AgeDistributionData("40-50", 0, 0, 41, 50),
     AgeDistributionData(">50", 0, 0, 51, null)
)