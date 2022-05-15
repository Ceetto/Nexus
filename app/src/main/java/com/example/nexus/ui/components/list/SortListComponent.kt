package com.example.nexus.ui.components.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RadioButtonChecked
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.SortOptions
import com.example.nexus.data.dataClasses.SortOptionsList
import com.example.nexus.ui.routes.lists.ListCategory


@Composable
fun SortListComponent(
    games: List<ListEntry>,
    toggleDescendingOrAscendingIcon: () -> Unit,
    getDescendingOrAscendingIcon:  () -> ImageVector,
    getSelectedCategory: () -> ListCategory,
    setSortOption: (String) -> Unit,
    getSortOption: () -> String
){
    Row(modifier = Modifier.fillMaxWidth()) {

        var expanded by remember { mutableStateOf(false) }

        IconButton(onClick = { expanded = !expanded }) {
            Icon(Icons.Default.Sort, contentDescription = "sort")
        }
        var entry = ""
        entry = if (games.size == 1){
            "entry"
        } else {
            "entries"
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            val sortOptions = ArrayList<String>(SortOptionsList)
            if (getSelectedCategory() == ListCategory.ALL){
                sortOptions.add(0, SortOptions.STATUS.value)
            }
            sortOptions.forEach { sortOption ->
                DropdownMenuItem(onClick = { expanded = false
                    setSortOption(sortOption) }) {
                    Row(){
                        var icon = Icons.Default.RadioButtonUnchecked
                        if (getSortOption() == sortOption){
                            icon = Icons.Default.RadioButtonChecked
                        }
                        Icon(icon, contentDescription = "sort option checked",
                            modifier = Modifier.padding(10.dp))
                        Text(text = sortOption, modifier = Modifier.padding(top = 10.dp))
                    }

                }
            }
        }

        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth(0.9f)){
            Text("${games.size} $entry", modifier = Modifier.padding(top = 10.dp))
        }
        Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()){
            IconButton(onClick = { toggleDescendingOrAscendingIcon()
            }) {
                Icon(getDescendingOrAscendingIcon(), contentDescription = "asc-desc")
            }
        }
    }
}