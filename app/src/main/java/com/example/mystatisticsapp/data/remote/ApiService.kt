package com.example.mystatisticsapp.data.remote

import com.example.mystatisticsapp.data.model.StatisticDto
import com.example.mystatisticsapp.data.model.StatisticksDto
import com.example.mystatisticsapp.data.model.UsersDto
import retrofit2.http.GET

interface ApiService {

     @GET("users/")
     suspend fun getUsers(): UsersDto

     @GET("statistics/")
     suspend fun getStatistics(): StatisticksDto
}