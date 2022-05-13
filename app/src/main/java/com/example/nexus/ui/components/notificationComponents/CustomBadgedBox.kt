package com.example.nexus.ui.components.notificationComponents


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Badge
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.nexus.data.dataClasses.HomeNavigationItem

@Composable
fun CustomBadgedBox(
    item: HomeNavigationItem,
    text: String
) {
    Box(modifier = Modifier.fillMaxSize(0.6f)) {
        Icon(
            imageVector = item.icon,
            item.screen.route,
            modifier = Modifier.fillMaxSize()
        )
        Badge(
            modifier = Modifier.fillMaxSize(0.4f).align(Alignment.TopEnd),
            backgroundColor = MaterialTheme.colors.error,
            content = { Text(text) }
        )
    }
}