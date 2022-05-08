package com.example.nexus.ui.components.loginComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.R

@Composable
fun Logo() {
    Image(
        painter = rememberAsyncImagePainter(R.drawable.logo),
        contentDescription = "login logo",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(185.dp)
    )
}