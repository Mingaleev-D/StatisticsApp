package com.example.mystatisticsapp.domain.model

//data class User(
//     val id: Int,
//     val username: String,
//     val age: Int?,
//     val isOnline: Boolean,
//     val sex: String?,
//     val files: List<File>
//)
//
//data class File(
//     val id: Int,
//     val type: String,
//     val url: String
//)

data class User(
     val id: Int,
     val username: String,
     val sex: String, // Можно заменить на Enum Sex.MALE, Sex.FEMALE, если нужна строгая типизация
     val age: Int,
     val isOnline: Boolean,
     val avatarUrl: String? // URL аватара (nullable, так как аватар может отсутствовать)
)
