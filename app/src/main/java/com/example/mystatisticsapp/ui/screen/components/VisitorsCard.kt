package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.R
import com.example.mystatisticsapp.ui.theme.MyStatisticsAppTheme

@Composable
fun VisitorsCard(
     modifier: Modifier = Modifier,
     textQuantity: String = "0",
     textInfo: String = "нет информации",
     iconPainter: Painter = painterResource(id = R.drawable.up_arrow),
     chartPainter: Painter = painterResource(id = R.drawable.growth_chart),
) {
     Card(
          modifier = modifier
               .fillMaxWidth(),
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(
               containerColor = Color.White
          ),
     ) {
          Row(
               modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
          ) {
               Image(
                    painter = chartPainter,
                    contentDescription = null,
                    modifier = Modifier
                         .size(60.dp)
                         .align(Alignment.CenterVertically),
               )

               Spacer(modifier = Modifier.width(20.dp))

               Column(
                    modifier = Modifier.weight(1f)
               ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         Text(
                              text = textQuantity,
                              fontSize = 25.sp,
                              fontWeight = FontWeight.W700,
                              color = Color.Black
                         )
                         Spacer(modifier = Modifier.width(8.dp))

                         Icon(
                              painter = iconPainter,
                              contentDescription = null,
                              tint = Color(0xFF4CAF50),
                              modifier = Modifier.size(24.dp)
                         )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                         text = textInfo,
                         fontSize = 15.sp,
                         color = Color.DarkGray
                    )
               }
          }
     }
}

@Preview(showBackground = true)
@Composable
private fun VisitorsCardPreview() {
     MyStatisticsAppTheme {
          VisitorsCard(
          )
     }
}
//     Column(
//          modifier = Modifier.fillMaxWidth(),
//     ) {
//          Text(
//               text = "Посетители",
//               style = MaterialTheme.typography.bodyMedium,
//               color = MaterialTheme.colorScheme.onSurfaceVariant
//          )
//          Spacer(modifier = Modifier.height(8.dp))
//
//
//
//     }