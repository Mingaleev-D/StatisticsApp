package com.example.mystatisticsapp.data.model


import com.google.gson.annotations.SerializedName

data class FileDto(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)