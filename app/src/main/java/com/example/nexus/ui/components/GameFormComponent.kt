package com.example.nexus.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.web.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import proto.Game

@Composable
fun GameFormComponent(
    game: Game,
    vM: NexusGameDetailViewModel
){
    Column(verticalArrangement = Arrangement.Center) {
        Row(){
            IconButton(onClick = { vM.onGameFormOpenChanged(false) }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close game form",
                )
            }
        }
        var score by remember { mutableStateOf(0)}
        var status by remember { mutableStateOf(ListCategory.PLAYING.value)}
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Your score: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf("Select score")}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                listOf(1,2,3,4,5,6,7,8,9,10).forEach { score ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = score.toString()}) {
                        Text(text = score.toString(), color = Color.White)
                    }
                }
            }
        }

        Row(modifier = Modifier.padding(5.dp)){
            Text(text = "Status: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf("Select status")}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                ListCategory.values().forEach { status ->
                    DropdownMenuItem(onClick = { expanded = false
                        text = status.value}) {
                        Text(text = status.value, color = Color.White)
                    }
                }
            }
        }

        
        val listEntry = ListEntry(game.id, game.name, 10, 42, ListCategory.PLAYING.value,
            vM.getCoverWithId(game.cover.id)
                ?.let {
                    imageBuilder(
                        it.imageId,
                        ImageSize.COVER_BIG,
                        ImageType.JPEG
                    )
                })

        Button(onClick = {vM.storeListEntry(listEntry)}){
            Text(text = "Save")
        }
    }

}