package com.example.mystatisticsapp.ui.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.mystatisticsapp.data.model.FileDto
import com.example.mystatisticsapp.data.model.UserDto
import com.example.mystatisticsapp.domain.model.User

@Composable
fun UserListItem(
     user: User,
     modifier: Modifier = Modifier,
) {
     val avatarUrl = user.avatarUrl

     Column(
          modifier = modifier
               .fillMaxWidth()
               .height(62.dp)
               .background(Color.White)
               .padding(horizontal = 16.dp, vertical = 8.dp)
     ) {
          Row(
               modifier = Modifier.fillMaxWidth(),
               verticalAlignment = Alignment.CenterVertically
          ) {
               // Аватар пользователя и индикатор онлайн/офлайн
               Box(modifier = Modifier.size(38.dp)) {
                    AsyncImage(
                         model = avatarUrl,
                         contentDescription = "User avatar",
                         modifier = Modifier
                              .fillMaxSize()
                              .clip(CircleShape)
                              .background(Color.LightGray),
                         contentScale = ContentScale.Crop
                    )
                    // Индикатор онлайн/офлайн
                    if (user.isOnline) {
                         Box(
                              modifier = Modifier
                                   .align(Alignment.BottomEnd)
                                   .offset(x = 1.dp, y = 1.dp)
                                   .size(9.dp)
                                   .clip(CircleShape)
                                   .background(Color(0xFF4CD964))
                         )
                    }
               }

               Spacer(modifier = Modifier.width(16.dp))
               // Имя пользователя, возраст и эмодзи
               Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         Text(
                              text = user.username,
                              style = MaterialTheme.typography.bodyLarge.copy(
                                   fontWeight = FontWeight.SemiBold,
                                   fontSize = 15.sp
                              ),
                              maxLines = 1
                         )
                         // Возраст
                         // Возраст теперь не nullable в доменной модели User, но можно добавить проверку, если есть 0
                         if (user.age > 0) {
                              Text(
                                   text = ", ${user.age}",
                                   style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 16.sp
                                   ),
                                   modifier = Modifier.padding(start = 4.dp)
                              )
                         }
                         // Эмодзи (для примера, можно сделать динамическим)
                         // Для примера, добавим эмодзи в зависимости от ID или рандомно
                         val emoji = when (user.id) {
                              1 -> "🍒"
                              2 -> "😈"
                              else -> "" // Если нет эмодзи, пустая строка
                         }
                         if (emoji.isNotEmpty()) {
                              Text(
                                   text = emoji,
                                   fontSize = 20.sp,
                                   modifier = Modifier.padding(start = 8.dp)
                              )
                         }
                    }
               }
               // Стрелка справа
               Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Details",
                    tint = Color.LightGray,
                    modifier = Modifier.size(24.dp)
               )
          }
          // Разделитель (тонкая линия)
          Divider(
               color = Color.LightGray.copy(alpha = 0.5f),
               thickness = 0.5.dp,
               )
     }
}

// --- Предпросмотр ---
@Preview(showBackground = true)
@Composable
fun PreviewUserListItem() {
     val sampleUserDtos = listOf(
          UserDto(
               id = 1,
               username = "ann.aeom",
               age = 25,
               isOnline = true,
               sex = "женский",
               fileDtos = listOf(
                    FileDto(
                         id = 101,
                         type = "avatar",
                         url = "https://i.ibb.co/q7d3vK7/user1.jpg"
                    )
               ) // Замените на реальный URL
          ),
          UserDto(
               id = 2,
               username = "akimova_huiw",
               age = 23,
               isOnline = false, // Офлайн
               sex = "женский",
               fileDtos = listOf(
                    FileDto(
                         id = 102,
                         type = "avatar",
                         url = "https://i.ibb.co/Wc631sY/user2.jpg"
                    )
               ) // Замените на реальный URL
          ),
          UserDto(
               id = 3,
               username = "gulia.filova",
               age = 32,
               isOnline = true,
               sex = "женский",
               fileDtos = listOf(
                    FileDto(
                         id = 103,
                         type = "avatar",
                         url = "https://i.ibb.co/f4FhC1S/user3.jpg"
                    )
               ) // Замените на реальный URL
          ),
          UserDto(
               id = 4,
               username = "offline_user",
               age = 40,
               isOnline = false,
               sex = "мужской",
               fileDtos = listOf(
                    FileDto(
                         id = 104,
                         type = "avatar",
                         url = "https://i.ibb.co/R2W78Fj/user4.jpg"
                    )
               ) // Замените на реальный URL
          )
     )
     //     Column {
     //          sampleUserDtos.forEach { user ->
     //               UserListItem(user = user)
     //          }
     //     }
}