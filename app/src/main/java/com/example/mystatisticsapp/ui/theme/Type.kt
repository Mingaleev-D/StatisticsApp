package com.example.mystatisticsapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.R

val Gilroy = FontFamily(
     Font(R.font.gilroy_light, FontWeight.Light),
     Font(R.font.gilroy_regular, FontWeight.Normal),
     Font(R.font.gilroy_medium, FontWeight.Medium),
     Font(R.font.gilroy_semibold, FontWeight.SemiBold),
     Font(R.font.gilroy_bold, FontWeight.Bold),
)
val Typography = Typography(
     bodyLarge = TextStyle(
          fontFamily = Gilroy,
          fontWeight = FontWeight.Normal,
          fontSize = 16.sp,
          lineHeight = 24.sp,
          letterSpacing = 0.5.sp
     )
)