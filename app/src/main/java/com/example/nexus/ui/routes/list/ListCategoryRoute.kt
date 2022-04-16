package com.example.nexus.ui.routes.list

import androidx.compose.runtime.Composable
import androidx.compose.material.Text

@Composable
fun ListCategoryRoute(
    category: ListCategory,
) {
    Text(
        text = category.value
    )
}
