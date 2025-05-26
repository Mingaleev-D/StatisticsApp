package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.mystatisticsapp.data.model.ChartDataPoint
import com.example.mystatisticsapp.ui.theme.OrangeBackground
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.roundToInt


@Composable
fun VisitorLineChart(
     dataPoints: List<ChartDataPoint>,
     modifier: Modifier = Modifier,
     lineColor: Color = OrangeBackground,
     pointColor: Color = OrangeBackground,
     gridLineColor: Color = Color.LightGray.copy(alpha = 0.5f),
     backgroundColor: Color = Color.White,
     textColor: Color = Color.Gray,
     emptyDataText: String = "Нет данных для отображения графика"
) {
     // Состояние для управления всплывающим окном
     var showPopup by remember { mutableStateOf(false) }
     var selectedPointData by remember { mutableStateOf<ChartDataPoint?>(null) }
     var popupOffset by remember { mutableStateOf(IntOffset.Zero) }
     // Состояние для хранения абсолютной позиции Canvas на экране
     var canvasOffsetOnScreen by remember { mutableStateOf(Offset.Zero) }
     // Состояние для хранения размера Canvas (не используется в текущей логике popup, но было у вас)
     // var canvasSizePx by remember { mutableStateOf(Size.Zero) } // Если не нужен, можно убрать
     // Получаем текущую плотность
     val density = LocalDensity.current // <--- ПОЛУЧАЕМ DENSITY ЗДЕСЬ
     // Предварительно рассчитаем значения в Px, если они константы
     val tapTolerancePx = with(density) { 20.dp.toPx() }
     val popupHorizontalShiftPx = with(density) { 60.dp.toPx() }
     val popupVerticalShiftPx = with(density) { 80.dp.toPx() }

     if (dataPoints.isEmpty()) {
          Box(
               modifier = modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .background(backgroundColor, RoundedCornerShape(12.dp)),
               contentAlignment = Alignment.Center
          ) {
               Text(emptyDataText, color = textColor)
          }
          return
     }
     // Найдем минимальное и максимальное значение Y для масштабирования
     val minValue = dataPoints.minOfOrNull { it.count } ?: 0
     val maxValue = dataPoints.maxOfOrNull { it.count } ?: 0
     // Расчеты для масштабирования графика
     val yAxisRange = (maxValue - minValue).toFloat()
     val paddingScale = 0.2f
     val effectiveMinValue: Float
     val effectiveMaxValue: Float

     if (yAxisRange == 0f) {
          effectiveMinValue = minValue.toFloat() - 1f
          effectiveMaxValue = maxValue.toFloat() + 1f
     } else {
          effectiveMinValue = minValue - (yAxisRange * paddingScale).roundToInt().toFloat()
          effectiveMaxValue = maxValue + (yAxisRange * paddingScale).roundToInt().toFloat()
     }
     val effectiveYAxisRange = (effectiveMaxValue - effectiveMinValue).coerceAtLeast(1f)
     val textMeasurer = rememberTextMeasurer()
     val dateFormatter = DateTimeFormatter.ofPattern("dd.MM", Locale.ROOT)
     val popupDateFormatter = DateTimeFormatter.ofPattern("d MMMM", Locale("ru"))
     val labelTextStyle = MaterialTheme.typography.labelSmall.copy(
          fontSize = 12.sp,
          color = textColor
     )
     // Сохраняем вычисленные позиции точек для использования в pointerInput
     val chartPointsState = remember { mutableStateListOf<Offset>() }

     Card(
          modifier = modifier
               .fillMaxWidth()
               .height(250.dp)
               .padding(end = 16.dp),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = backgroundColor),
     ) {
          Canvas(
               modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 40.dp, start = 20.dp, end = 20.dp)
                    .onGloballyPositioned { coordinates ->
                         canvasOffsetOnScreen =
                              coordinates.localToScreen(Offset.Zero) // Используем localToScreen

                    }
                    .pointerInput(dataPoints) {
                         detectTapGestures(
                              onTap = { tapOffsetOnCanvas ->

                                   val closestPointIndex =
                                        chartPointsState.indexOfFirst { chartPointOffsetOnCanvas ->
                                             (tapOffsetOnCanvas - chartPointOffsetOnCanvas).getDistance() <= tapTolerancePx
                                        }

                                   if (closestPointIndex != -1) {
                                        selectedPointData = dataPoints[closestPointIndex]
                                        val tappedChartOffsetOnCanvas =
                                             chartPointsState[closestPointIndex]
                                        // Вычисляем popupOffset с учетом глобальной позиции Canvas
                                        // tapOffsetOnCanvas X и Y - это координаты внутри Canvas
                                        // canvasOffsetOnScreen X и Y - это смещение Canvas относительно экрана
                                        popupOffset = IntOffset(
                                             x = (canvasOffsetOnScreen.x + tappedChartOffsetOnCanvas.x - popupHorizontalShiftPx).roundToInt(),
                                             y = (canvasOffsetOnScreen.y + tappedChartOffsetOnCanvas.y - popupVerticalShiftPx).roundToInt()
                                        )
                                        showPopup = true
                                   } else {
                                        showPopup = false
                                        selectedPointData = null
                                   }
                              }
                         )
                    }
          ) {
               val width = size.width
               val height = size.height

               if (dataPoints.isEmpty()) {
                    return@Canvas
               }
               // Очищаем и заново заполняем chartPointsState
               chartPointsState.clear()
               // Рассчитываем позиции точек и сохраняем их
               dataPoints.mapIndexed { index, dataPoint ->
                    val x = if (dataPoints.size == 1) {
                         width / 2f
                    } else {
                         (index.toFloat() / (dataPoints.size - 1).toFloat()) * width
                    }
                    val yValue = dataPoint.count.toFloat()
                    val y = height - ((yValue - effectiveMinValue) / effectiveYAxisRange) * height
                    val calculatedOffset = Offset(x.coerceIn(0f, width), y.coerceIn(0f, height))
                    chartPointsState.add(calculatedOffset) // Сохраняем для обнаружения нажатий
                    calculatedOffset
               }
               // --- Рисуем пунктирные горизонтальные линии сетки ---
               val numberOfHorizontalLines = 3
               if (numberOfHorizontalLines > 1) {
                    for (i in 0 until numberOfHorizontalLines) {
                         val yLine = height - (i.toFloat() / (numberOfHorizontalLines - 1)) * height
                         drawLine(
                              color = gridLineColor,
                              start = Offset(0f, yLine),
                              end = Offset(width, yLine),
                              strokeWidth = 1.dp.toPx(),
                              pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                         )
                    }
               }
               // --- Рисуем линию графика ---
               if (chartPointsState.size >= 2) {
                    val path = Path().apply {
                         moveTo(chartPointsState.first().x, chartPointsState.first().y)
                         for (i in 1 until chartPointsState.size) {
                              lineTo(chartPointsState[i].x, chartPointsState[i].y)
                         }
                    }
                    drawPath(
                         path = path,
                         color = lineColor,
                         style = Stroke(width = 3.dp.toPx())
                    )
               }
               // --- Рисуем точки на линии (или одну точку) ---
               chartPointsState.forEach { offset ->
                    drawCircle(pointColor, radius = 6.dp.toPx(), center = offset)
                    drawCircle(
                         backgroundColor,
                         radius = 4.dp.toPx(),
                         center = offset,
                         style = Stroke(width = 1.dp.toPx())
                    )
               }
               // --- Рисуем метки дат на оси X ---
               if (chartPointsState.size == dataPoints.size) {
                    dataPoints.forEachIndexed { index, dataPoint ->
                         val xLabel = chartPointsState[index].x
                         val dateText = dataPoint.date.format(dateFormatter)
                         val textLayoutResult = textMeasurer.measure(
                              text = dateText,
                              style = labelTextStyle
                         )
                         drawText(
                              textLayoutResult = textLayoutResult,
                              topLeft = Offset(
                                   xLabel - textLayoutResult.size.width / 2,
                                   height + 10.dp.toPx()
                              )
                         )
                    }
               }
          }
     }
     // --- Всплывающее окно ---
     if (showPopup && selectedPointData != null) {
          Popup(
               offset = popupOffset, // IntOffset
               onDismissRequest = { showPopup = false },
               properties = PopupProperties(focusable = true)
          ) {
               PointInfoPopupContent(
                    dataPoint = selectedPointData!!,
                    dateFormatter = remember {
                         DateTimeFormatter.ofPattern(
                              "d MMMM",
                              Locale("ru")
                         )
                    } // Пример
               )
          }
     }
}

// --- Composable для содержимого всплывающего окна ---
@Composable
fun PointInfoPopupContent(
     dataPoint: ChartDataPoint,
     dateFormatter: DateTimeFormatter
) {
     Card(
          modifier = Modifier
               .width(IntrinsicSize.Min) // Ширина по содержимому
               .padding(8.dp), // Отступы вокруг карточки
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
     ) {
          Column(
               modifier = Modifier.padding(12.dp), // Внутренние отступы
               horizontalAlignment = Alignment.Start
          ) {
               Text(
                    text = "${dataPoint.count} посетитель",
                    fontSize = 16.sp,
                    color = Color(0xFFFF3B30), // Красный цвет, как на скриншоте
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
               )
               Spacer(modifier = Modifier.height(4.dp))
               Text(
                    text = dataPoint.date.format(dateFormatter),
                    fontSize = 14.sp,
                    color = Color.Gray
               )
          }
     }
}

// Вспомогательная функция для генерации фейковых данных для предпросмотра
// Нам нужны данные, отсортированные по дате
fun generateFakeChartData(): List<ChartDataPoint> {
     val today = LocalDate.now()
     return listOf(
          ChartDataPoint(today.minusDays(6), 25),
          ChartDataPoint(today.minusDays(5), 32),
          ChartDataPoint(today.minusDays(4), 28),
          ChartDataPoint(today.minusDays(3), 45),
          ChartDataPoint(today.minusDays(2), 38),
          ChartDataPoint(today.minusDays(1), 20),
          ChartDataPoint(today, 55) // Текущий день
     ).sortedBy { it.date }
}

@Preview(showBackground = true, name = "Visitor Line Chart with Data")
@Composable
fun PreviewVisitorLineChart() {
     MaterialTheme { // Обертка в MaterialTheme для корректного отображения стилей в Preview
          val fakeData = generateFakeChartData()
          VisitorLineChart(dataPoints = fakeData)
     }
}

@Preview(showBackground = true, name = "Visitor Line Chart Empty")
@Composable
fun PreviewVisitorLineChartEmpty() {
     MaterialTheme {
          VisitorLineChart(dataPoints = emptyList())
     }
}

@Preview(showBackground = true, name = "Visitor Line Chart One Point")
@Composable
fun PreviewVisitorLineChartOnePoint() {
     MaterialTheme {
          val today = LocalDate.now()
          val singlePointData = listOf(ChartDataPoint(today, 30))
          VisitorLineChart(dataPoints = singlePointData)
     }
}

@Preview(showBackground = true, name = "Visitor Line Chart All Same Values")
@Composable
fun PreviewVisitorLineChartSameValues() {
     MaterialTheme {
          val today = LocalDate.now()
          val sameValueData = listOf(
               ChartDataPoint(today.minusDays(2), 30),
               ChartDataPoint(today.minusDays(1), 30),
               ChartDataPoint(today, 30)
          ).sortedBy { it.date }
          VisitorLineChart(dataPoints = sameValueData)
     }
}