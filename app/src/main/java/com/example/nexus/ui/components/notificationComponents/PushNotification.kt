package com.example.nexus.ui.components.notificationComponents


import android.content.Context
import androidx.compose.runtime.Composable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.nexus.R

@Composable
fun PushNotification(
    context: Context,
    channelId: String,
    notificationId: Int,
    textTitle: String,
    textContent: String,
    priority: Int = NotificationCompat.PRIORITY_DEFAULT
) {
    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.logo)
        .setContentTitle(textTitle)
        .setContentText(textContent)
        .setPriority(priority)
    with(NotificationManagerCompat.from(context)) {
        notify(notificationId, builder.build())
    }
}