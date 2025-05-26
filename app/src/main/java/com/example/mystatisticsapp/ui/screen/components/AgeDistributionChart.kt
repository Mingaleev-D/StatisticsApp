package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.data.model.AgeDistributionData
import com.example.mystatisticsapp.ui.theme.OrangeBackground
import com.example.mystatisticsapp.ui.theme.OrangeLightBackground

@Composable
fun AgeDistributionChart(
     ageData: List<AgeDistributionData>,
     modifier: Modifier = Modifier,
     primaryBarColor: Color = OrangeBackground,
     secondaryBarColor: Color = OrangeLightBackground,
     emptyDataText: String = "Нет данных по возрасту",
     maxLineLength: Dp = 80.dp,
     lineHeight: Dp = 4.dp,
     dotRadius: Dp = 3.dp
) {
     val textMeasurer = rememberTextMeasurer()
     val density = LocalDensity.current

     Card(
          modifier = modifier
               .fillMaxWidth()
               .height(400.dp)
               .padding(end = 16.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
     ) {
          if (ageData.isEmpty() || ageData.all { it.totalGroupCount == 0 }) {
               Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
               ) {
                    Text(emptyDataText, color = Color.Gray)
               }
               return@Card
          }
          // Вычисляем общий итог для всех групп, чтобы проценты были от общего числа пользователей
          val totalUsersOverall = ageData.sumOf { it.totalGroupCount }.toFloat()
          // Находим максимальный процент, чтобы масштабировать линии относительно него
          // Это может быть 100%, если мы хотим, чтобы 100% всегда занимало maxLineLength
          // Или максимальный процент из данных, если хотим, чтобы самый длинный бар был maxLineLength
          val maxPercentageForScaling = 100f // Если 100% всегда соответствует maxLineLength

          Column(
               modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
          ) {
               // Вычисляем максимальную ширину метки для выравнивания
               val maxLabelWidth = with(density) {
                    ageData.maxOf {
                         textMeasurer.measure(
                              text = it.label,
                              style = TextStyle(fontSize = 10.sp)
                         ).size.width.toDp()
                    }
               } + 18.dp

               ageData.forEach { data ->
                    Row(
                         modifier = Modifier
                              .fillMaxWidth()
                              .height(40.dp),
                         verticalAlignment = Alignment.CenterVertically
                    ) {
                         // Метка возрастной группы
                         Text(
                              text = data.label,
                              style = MaterialTheme.typography.bodyLarge.copy(
                                   fontWeight = FontWeight.SemiBold,
                                   fontSize = 15.sp,
                                   textAlign = TextAlign.End
                              ),
                              modifier = Modifier.width(maxLabelWidth),
                         )

                         Spacer(modifier = Modifier.width(26.dp))

                         Column(modifier = Modifier.weight(1f)) {
                              // Первый бар (Primary Value - Красный)
                              val primaryPercentage =
                                   if (totalUsersOverall > 0) (data.countPrimary.toFloat() / totalUsersOverall) * 100f else 0f
                              AgeChartSingleLine(
                                   percentage = primaryPercentage,
                                   color = primaryBarColor,
                                   maxLineLength = maxLineLength,
                                   lineHeight = lineHeight,
                                   dotRadius = dotRadius,
                                   maxPercentage = maxPercentageForScaling
                              )

                              Spacer(modifier = Modifier.height(4.dp))
                              // Второй бар (Secondary Value - Оранжевый)
                              val secondaryPercentage =
                                   if (totalUsersOverall > 0) (data.countSecondary.toFloat() / totalUsersOverall) * 100f else 0f
                              AgeChartSingleLine(
                                   percentage = secondaryPercentage,
                                   color = secondaryBarColor,
                                   maxLineLength = maxLineLength,
                                   lineHeight = lineHeight,
                                   dotRadius = dotRadius,
                                   maxPercentage = maxPercentageForScaling
                              )
                         }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
               }
          }
     }
}

// Вспомогательная Composable для отрисовки одной линии/точки графика
@Composable
fun AgeChartSingleLine(
     percentage: Float,
     color: Color,
     maxLineLength: Dp,
     lineHeight: Dp,
     dotRadius: Dp,
     maxPercentage: Float
) {
     Row(verticalAlignment = Alignment.CenterVertically) {
          // Линия/точка
          Canvas(
               modifier = Modifier
                    .width(maxLineLength) // Область для рисования линии
                    .height(lineHeight) // Высота Canvas соответствует высоте линии
          ) {
               // Фактическая длина линии, пропорциональная проценту от maxPercentage
               val actualLength = (percentage / maxPercentage) * size.width

               if (percentage > 0) {
                    // Рисуем линию
                    drawLine(
                         color = color,
                         start = Offset(0f, size.height / 2f),
                         end = Offset(actualLength, size.height / 2f),
                         strokeWidth = lineHeight.toPx(),
                         cap = StrokeCap.Round
                    )
               } else {
                    // Рисуем точку, если процент 0
                    drawCircle(
                         color = color,
                         radius = dotRadius.toPx(),
                         center = Offset(dotRadius.toPx(), size.height / 2f) // Центр точки
                    )
               }
          }

         // Spacer(modifier = Modifier.width(8.dp))
          // Текст процента
          Text(
               text = "${"%.0f".format(percentage)}%",
               style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
               )
          )
     }
}

