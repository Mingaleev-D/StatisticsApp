package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.ui.theme.OrangeBackground
import com.example.mystatisticsapp.ui.theme.ScaffoldBackground

@Composable
fun TimeRangeSelector(
     selectedRange: TimeRange,
     onRangeSelected: (TimeRange) -> Unit,
     modifier: Modifier = Modifier
) {
     LazyRow(
          modifier = modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          contentPadding = PaddingValues(top = 8.dp)
     ) {
          item {
               PeriodButton(
                    text = "Сегодня",
                    isSelected = selectedRange == TimeRange.TODAY,
                    onClick = { onRangeSelected(TimeRange.TODAY) },
               )
          }
          item {
               PeriodButton(
                    text = "Неделя",
                    isSelected = selectedRange == TimeRange.WEEK,
                    onClick = { onRangeSelected(TimeRange.WEEK) },
               )
          }
          item {
               PeriodButton(
                    text = "Месяц",
                    isSelected = selectedRange == TimeRange.MONTH,
                    onClick = { onRangeSelected(TimeRange.MONTH) },
               )
          }
          item {
               PeriodButton(
                    text = "Все время",
                    isSelected = selectedRange == TimeRange.ALL_TIME,
                    onClick = { onRangeSelected(TimeRange.ALL_TIME) },
                    )
          }
     }
}

// Вспомогательная Composable для одной кнопки периода (та же, что и раньше)
@Composable
private fun PeriodButton(
     text: String,
     isSelected: Boolean,
     onClick: () -> Unit,
     modifier: Modifier = Modifier
) {
     val backgroundColor = if (isSelected) OrangeBackground else ScaffoldBackground
     val textColor = if (isSelected) Color.White else Color.Black
     val borderStroke = if (isSelected) null else BorderStroke(
          1.dp,
          Color(0xFFE0E0E0)
     )

     Surface(
          modifier = modifier
               .defaultMinSize(minHeight = 32.dp)
               .clickable(onClick = onClick),
          shape = RoundedCornerShape(49.dp),
          color = backgroundColor,
          border = borderStroke,
     ) {
          Box(
               contentAlignment = Alignment.Center,
               modifier = Modifier
                    .fillMaxSize()
          ) {
               Text(
                    modifier = Modifier.padding(
                         top = 12.dp,
                         start = 16.dp,
                         end = 16.dp,
                         bottom = 12.dp
                    ),
                    text = text,
                    color = textColor,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
               )
          }
     }
}

// Определим варианты временных диапазонов
enum class TimeRange {
     TODAY,       // "Сегодня"
     WEEK,        // "Неделя"
     MONTH,       // "Месяц"
     ALL_TIME     // "Все время"
}

// --- Предпросмотр ---
@Preview(showBackground = true)
@Composable
fun PreviewTimeRangeSelector() {
     var selectedRange by remember { mutableStateOf(TimeRange.TODAY) } // Изначально выбрано "Сегодня"

     Column(modifier = Modifier.fillMaxSize()) {
          TimeRangeSelector(
               selectedRange = selectedRange,
               onRangeSelected = { newRange ->
                    selectedRange = newRange
               }
          )
     }
}