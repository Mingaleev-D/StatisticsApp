package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.* // Для remember и mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.ui.theme.OrangeBackground
import com.example.mystatisticsapp.ui.theme.ScaffoldBackground

/**
Переключение сделал неактивным так как в условиях про это ничего не сказано
 */
@Composable
fun TimePeriodSelector(
     selectedPeriod: TimePeriod,
     onPeriodSelected: (TimePeriod) -> Unit,
     modifier: Modifier = Modifier
) {
     LazyRow(
          modifier = modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          // contentPadding = PaddingValues(horizontal = 8.dp)
     ) {
          item {
               PeriodButton(
                    text = "По дням",
                    isSelected = selectedPeriod == TimePeriod.DAILY,
                    onClick = {
                         null
                         //onPeriodSelected(TimePeriod.DAILY)
                    })
          }
          item {
               PeriodButton(
                    text = "По неделям",
                    isSelected = selectedPeriod == TimePeriod.WEEKLY,
                    onClick = {
                         null
                         // onPeriodSelected(TimePeriod.WEEKLY)
                    },
               )
          }
          item {
               PeriodButton(
                    text = "По месяцам",
                    isSelected = selectedPeriod == TimePeriod.MONTHLY,
                    onClick = {
                         null
                         // onPeriodSelected(TimePeriod.MONTHLY)
                    },
               )
          }
     }
}

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

// Определим варианты временных периодов
enum class TimePeriod {
     DAILY,    // "По дням"
     WEEKLY,   // "По неделям"
     MONTHLY   // "По месяцам"
}

@Preview(showBackground = true)
@Composable
fun PreviewTimePeriodSelector() {
     var selectedPeriod by remember { mutableStateOf(TimePeriod.DAILY) }

     Column(modifier = Modifier.fillMaxSize()) {
          TimePeriodSelector(
               selectedPeriod = selectedPeriod,
               onPeriodSelected = { newPeriod ->
                    selectedPeriod = newPeriod
               })
     }
}

@Preview(showBackground = true)
@Composable
fun PreviewPeriodButtonSelected() {
     PeriodButton(text = "По дням", isSelected = true, onClick = {})
}

@Preview(showBackground = true)
@Composable
fun PreviewPeriodButtonUnselected() {
     PeriodButton(text = "По неделям", isSelected = false, onClick = {})
}