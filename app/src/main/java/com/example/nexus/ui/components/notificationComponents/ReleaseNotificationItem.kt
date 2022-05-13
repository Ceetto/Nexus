package com.example.nexus.ui.components.notificationComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R
import com.example.nexus.data.dataClasses.Notification

@Composable
fun ReleaseNotificationItem(
    notification: Notification,
    removeNotification: (Notification) -> Unit
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(notification.gameCover),
                contentDescription = "game_cover",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
            )
        }
        Column(modifier = Modifier.fillMaxWidth(0.9f)) {
            Text(
                text = notification.gameName,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )
            Text(
                text = notification.gameName + " Is releasing soon!"
            )
        }
        IconButton(onClick = { removeNotification(notification) }, modifier = Modifier.size(22.dp)) {
            Icon(
                imageVector = Icons.Filled.Clear,
                contentDescription = "Remove notification"
            )
        }
    }
}