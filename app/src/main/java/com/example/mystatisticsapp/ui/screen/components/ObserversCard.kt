package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mystatisticsapp.R

@Composable
fun ObserversCard() {
     Column(
          modifier = Modifier.padding(end = 16.dp)
     ) {
          VisitorsCard(
               textQuantity = "1234",
               textInfo = "Новые наблюдатели в этом месяце",
          )
          VisitorsCard(
               textQuantity = "10",
               textInfo = "Пользователей перестали за Вами наблюдать",
               iconPainter = painterResource(R.drawable.pointer_down),
               chartPainter = painterResource(R.drawable.negative_chart),
          )
     }
}