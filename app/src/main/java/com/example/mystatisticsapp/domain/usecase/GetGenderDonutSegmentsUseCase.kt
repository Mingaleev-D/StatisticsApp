package com.example.mystatisticsapp.domain.usecase

import com.example.mystatisticsapp.data.model.DonutChartSegment
import com.example.mystatisticsapp.data.model.GenderDistributionData
import com.example.mystatisticsapp.domain.model.User
import com.example.mystatisticsapp.ui.theme.OrangeBackground
import com.example.mystatisticsapp.ui.theme.OrangeLightBackground
import javax.inject.Inject


val MaleColor = OrangeBackground
val FemaleColor = OrangeLightBackground

class GetGenderDonutSegmentsUseCase @Inject constructor() {
     operator fun invoke(users: List<User>): List<DonutChartSegment> {
          val genderData = processGenderDistribution(users)
          return mapGenderDataToDonutSegments(genderData)
     }

     private fun processGenderDistribution(users: List<User>): GenderDistributionData {
          var maleCount = 0
          var femaleCount = 0
          var otherCount = 0

          users.forEach { user ->
               when (user.sex.lowercase()) {
                    "м", "m", "мужской", "male" -> maleCount++
                    "ж", "w", "женский", "female" -> femaleCount++
                    else -> otherCount++
               }
          }
          return GenderDistributionData(maleCount, femaleCount, otherCount)
     }

     private fun mapGenderDataToDonutSegments(genderData: GenderDistributionData): List<DonutChartSegment> {
          val segments = mutableListOf<DonutChartSegment>()
          val total = genderData.total.toFloat()

          if (total == 0f) {
               return emptyList()
          }

          if (genderData.maleCount > 0) {
               segments.add(
                    DonutChartSegment(
                         label = "Мужчины",
                         value = (genderData.maleCount / total) * 100f,
                         color = MaleColor
                    )
               )
          }
          if (genderData.femaleCount > 0) {
               segments.add(
                    DonutChartSegment(
                         label = "Женщины",
                         value = (genderData.femaleCount / total) * 100f,
                         color = FemaleColor
                    )
               )
          }
          return segments.sortedByDescending { it.value }
     }
}