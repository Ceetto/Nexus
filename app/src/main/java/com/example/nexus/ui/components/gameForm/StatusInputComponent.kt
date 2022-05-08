package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.routes.ListCategoryColors

@Composable
fun StatusInput(
    getGameStatus: () -> String,
    setGameStatus: (String) -> Unit,
    weight1: Float,
    weight2: Float
){
    Row(modifier = Modifier.padding(5.dp)){
        Text(text = "Status: ", modifier = Modifier.padding(top = 10.dp).weight(weight1, true))
        var expanded by remember { mutableStateOf(false) }
        var text by remember { mutableStateOf(getGameStatus()) }
        OutlinedButton(onClick = { expanded = !expanded }, modifier = Modifier.weight(weight2, true)) {
            ListCategoryColors[text]?.let { Text(text = text, color= it) }
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

            listOf(
                ListCategory.PLAYING, ListCategory.PLANNED, ListCategory.COMPLETED,
                ListCategory.DROPPED).forEach { status ->
                DropdownMenuItem(onClick = { expanded = false
                    text = status.value
                    setGameStatus(status.value)}) {
                    ListCategoryColors[status.value]
                        ?.let { Text(text = status.value, color = it) }
                }
            }
        }
    }
}