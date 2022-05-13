package com.example.nexus.data.dataClasses

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nexus.ui.navigation.Screen

data class HomeNavigationItem(val screen: Screen,
                              val icon: ImageVector,
                              val label: String
)