package com.example.nexus.ui.components.notificationComponents

import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FriendNotificationItem(type: String, detail: String) {
    Row( // 1
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(start = 8.dp)) { // 3
            Text(
                text = type,
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 18.sp,
            )

            Text(
                text = detail,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(onClick = { /*TODO*/ },
                    content = { Text(text = "Accept") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xff2ECC71))
                )
                Spacer(modifier = Modifier.width(16.dp))
                OutlinedButton(onClick = { /*TODO*/ },
                    content = { Text(text = "Decline") },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xffED4245))
                )
            }
        }
    }
}