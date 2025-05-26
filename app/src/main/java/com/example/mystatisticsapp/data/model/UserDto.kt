package com.example.mystatisticsapp.data.model


import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("age")
    val age: Int?,
    @SerializedName("files")
    val fileDtos: List<FileDto?>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("isOnline")
    val isOnline: Boolean?,
    @SerializedName("sex")
    val sex: String?,
    @SerializedName("username")
    val username: String?
)