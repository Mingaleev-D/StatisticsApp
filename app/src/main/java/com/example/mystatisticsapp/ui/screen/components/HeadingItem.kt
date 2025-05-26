package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.ui.theme.MyStatisticsAppTheme

@Composable
fun HeadingItem(
     modifier: Modifier = Modifier,
     txt: String,
     fSize: TextUnit = MaterialTheme.typography.bodyLarge.fontSize,
     fontWeight: FontWeight? = null,
     ) {
     Text(
          text = txt,
          modifier = modifier,
          style = MaterialTheme.typography.bodyLarge.copy(
               fontSize = fSize,
               fontWeight = fontWeight
          )
     )
}

@Preview(showBackground = true)
@Composable
private fun HeadingItemPreview() {
     MyStatisticsAppTheme {
          HeadingItem(
               txt = "Статистика",
               fSize = 32.sp,
               fontWeight = FontWeight.Bold
          )
     }
}