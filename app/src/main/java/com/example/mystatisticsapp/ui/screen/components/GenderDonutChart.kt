package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mystatisticsapp.data.model.UserDto
import com.example.mystatisticsapp.data.model.UsersDto
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.data.model.DonutChartSegment

@Composable
fun GenderDonutChart(
     segments: List<DonutChartSegment>,
     modifier: Modifier = Modifier,
     chartStrokeWidth: Dp = 15.dp,
     chartSize: Dp = 150.dp,
     backgroundColor: Color = Color.White,
     emptyDataText: String = "Нет данных о пользователях",
     segmentSpacingAngle: Float = 15f
) {
     Card(
          modifier = modifier
               .fillMaxWidth()
               .padding(top = 16.dp, end = 16.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = backgroundColor),
     ) {
          Column(
               modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
               horizontalAlignment = Alignment.CenterHorizontally
          ) {
               if (segments.isEmpty()) {
                    Box(
                         modifier = Modifier
                              .size(chartSize)
                              .background(Color.Transparent, CircleShape),
                         contentAlignment = Alignment.Center
                    ) {
                         Text(emptyDataText, color = Color.Gray, fontSize = 14.sp)
                    }
               } else {
                    Canvas(modifier = Modifier.size(chartSize)) {
                         val strokeWidthPx = chartStrokeWidth.toPx()
                         val diameter = size.minDimension
                         val radius = (diameter / 2f) - (strokeWidthPx / 2f)
                         val actualDiameter = radius * 2f
                         val topLeft = Offset(
                              x = (size.width - actualDiameter) / 2f,
                              y = (size.height - actualDiameter) / 2f
                         )
                         val arcSize = Size(actualDiameter, actualDiameter)
                         var startAngle = -90f
                         val numberOfSpacers = segments.size
                         val totalSpacingAngle = segmentSpacingAngle * numberOfSpacers
                         val totalAvailableAngleForSegments = 360f - totalSpacingAngle

                         segments.forEach { segment ->
                              val sweepAngle =
                                   (segment.value / 100f) * totalAvailableAngleForSegments
                              val effectiveSweepAngle = sweepAngle.coerceAtLeast(0.001f)


                              drawArc(
                                   color = segment.color,
                                   startAngle = startAngle,
                                   sweepAngle = effectiveSweepAngle,
                                   useCenter = false,
                                   topLeft = topLeft,
                                   size = arcSize,
                                   style = Stroke(
                                        width = strokeWidthPx,
                                        cap = StrokeCap.Round
                                   )
                              )

                              startAngle += effectiveSweepAngle + segmentSpacingAngle
                         }
                    }
               }

               Spacer(modifier = Modifier.height(20.dp))

               Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
               ) {
                    segments.forEachIndexed { index, segment ->
                         if (index > 0) {
                              Spacer(modifier = Modifier.width(20.dp))
                         }
                         LegendItem(
                              segment.label,
                              segment.value,
                              segment.color
                         )
                    }
               }
          }
     }
}

// LegendItem остается без изменений
@Composable
fun LegendItem(label: String, value: Float, color: Color) {
     Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
               modifier = Modifier
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(color)
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
               text = "$label ${"%.0f".format(value)}%",
               style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
               )
          )
     }
}

// --- Предпросмотр ---
@Preview(showBackground = true)
@Composable
fun PreviewGenderDonutChart() {
     // Генерируем фейковые данные для предпросмотра
     val fakeUsersDto = UsersDto(
          userDtos = listOf(
               UserDto(
                    id = 1,
                    username = "A",
                    sex = "мужской",
                    age = 20,
                    isOnline = true,
                    fileDtos = emptyList()
               ),
               UserDto(
                    id = 2,
                    username = "B",
                    sex = "женский",
                    age = 25,
                    isOnline = false,
                    fileDtos = emptyList()
               ),
               UserDto(
                    id = 3,
                    username = "C",
                    sex = "мужской",
                    age = 30,
                    isOnline = true,
                    fileDtos = emptyList()
               ),
               UserDto(
                    id = 4,
                    username = "D",
                    sex = "женский",
                    age = 35,
                    isOnline = false,
                    fileDtos = emptyList()
               ),
               UserDto(
                    id = 5,
                    username = "E",
                    sex = "мужской",
                    age = 40,
                    isOnline = true,
                    fileDtos = emptyList()
               )
          )
     )
}

@Preview(showBackground = true)
@Composable
fun PreviewGenderDonutChartEmpty() {
     GenderDonutChart(segments = emptyList())
}