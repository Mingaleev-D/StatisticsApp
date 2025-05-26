package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mystatisticsapp.R
import com.example.mystatisticsapp.ui.theme.MyStatisticsAppTheme

@Composable
fun VisitorsOverviewCard(
     modifier: Modifier = Modifier
) {
     Column(
          modifier = modifier.fillMaxWidth()
     ) {
          HeadingItem(
               txt = stringResource(R.string.users_txt),
               fSize = 20.sp,
               fontWeight = FontWeight.W700,
          )
          Spacer(modifier = Modifier.height(16.dp))
          VisitorsCard(
               textQuantity = "1356",
               textInfo = "Количество посетителей в этом месяце выросло",
               modifier = Modifier.padding(end = 16.dp),
          )
     }
}

@Preview(showBackground = true)
@Composable
private fun VisitorsOverviewCardPreview() {
     MyStatisticsAppTheme {
          VisitorsOverviewCard()
     }
}