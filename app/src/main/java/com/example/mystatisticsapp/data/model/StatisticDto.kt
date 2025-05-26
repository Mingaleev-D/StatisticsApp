package com.example.mystatisticsapp.data.model

import com.google.gson.annotations.SerializedName

data class StatisticDto(
     @SerializedName("dates")
     val dates: List<Int?>?,
     @SerializedName("type")
     val type: String?,
     @SerializedName("user_id")
     val userId: Int?
)