package com.example.nexus.ui.components.notificationComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReleaseNotificationItem(type: String, detail: String) {
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
        }
    }
}