package com.example.mystatisticsapp.domain.usecase

import com.example.mystatisticsapp.data.mappers.AGE_GROUP_DEFINITIONS
import com.example.mystatisticsapp.data.model.AgeDistributionData
import com.example.mystatisticsapp.domain.model.User
import javax.inject.Inject


class GetAgeDistributionDataUseCase @Inject constructor() {
     operator fun invoke(users: List<User>): List<AgeDistributionData> {
          val ageGroupsMap = AGE_GROUP_DEFINITIONS.associateBy { it.label }
               .mapValues { it.value.copy(countPrimary = 0, countSecondary = 0) }
               .toMutableMap()

          users.forEach { user ->
               val age = user.age
               if (age != null && age >= 0) {
                    val isMale = user.sex.lowercase() == "м" || user.sex.lowercase() == "m" ||
                         user.sex.lowercase() == "мужской" || user.sex.lowercase() == "male"
                    val isFemale = user.sex.lowercase() == "ж" || user.sex.lowercase() == "w" ||
                         user.sex.lowercase() == "женский" || user.sex.lowercase() == "female"

                    for (groupDef in AGE_GROUP_DEFINITIONS) {
                         if (age >= (groupDef.minAge ?: 0) && (groupDef.maxAge == null || age <= groupDef.maxAge)) {
                              val currentGroup = ageGroupsMap[groupDef.label]!!
                              if (isMale) {
                                   ageGroupsMap[groupDef.label] = currentGroup.copy(countPrimary = currentGroup.countPrimary + 1)
                              } else if (isFemale) {
                                   ageGroupsMap[groupDef.label] = currentGroup.copy(countSecondary = currentGroup.countSecondary + 1)
                              }
                              break
                         }
                    }
               }
          }
          return AGE_GROUP_DEFINITIONS.map { groupDef ->
               ageGroupsMap[groupDef.label]!!
          }
     }
}
