package com.example.nexus.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.web.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import com.example.nexus.viewmodels.games.NexusGameFormViewModel
import proto.Game
import kotlin.math.roundToInt

@Composable
fun GameFormComponent(
    game: Game,
    vM: NexusGameDetailViewModel
){
    val focusManager = LocalFocusManager.current
    Column(verticalArrangement = Arrangement.Top,
            modifier = Modifier.pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })}.fillMaxSize()) {
        Row(){
            IconButton(onClick = { vM.onGameFormOpenChanged(false)
                                    focusManager.clearFocus()}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close game form",
                )
            }
        }
//        var gameScore by remember { mutableStateOf(0)}
//        var gameStatus by remember { mutableStateOf(ListCategory.PLAYING.value)}

        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Your score: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf("Select score")}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(onClick = { expanded = false
                                            text = "No score"
                                            vM.setGameScore(0)}) {
                    Text(text = "No score")
                }
                listOf(1,2,3,4,5,6,7,8,9,10).forEach { score ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = score.toString()
                                                vM.setGameScore(score)}) {
                        Text(text = score.toString(), color = Color.White)
                    }
                }
            }
        }

        Row(modifier = Modifier.padding(5.dp)){
            Text(text = "Status: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf(ListCategory.PLAYING.value)}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                ListCategory.values().forEach { status ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = status.value
                                                vM.setGameStatus(status.value)}) {
                        Text(text = status.value, color = Color.White)
                    }
                }
            }
        }
//        var hours by remember { mutableStateOf("0")}
//        var minutes by remember { mutableStateOf("0")}

        Row(modifier = Modifier
            .padding(5.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }){
            Text(text = "Hours played: ")
            TextField(
                value = vM.getHours(),
                onValueChange = { value ->
                        vM.setHours(value)
                    },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
            }

        Row(modifier = Modifier
            .padding(5.dp)){
            Text(text = "Minutes played: ")
            TextField(
                value = vM.getMinutes(),
                onValueChange = { value ->
                    vM.setMinutes(value)
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = {
                    focusManager.clearFocus()
                })
            )
        }

        Button(onClick = {
            val intHours = vM.getHours().toIntOrNull()
            val intMinutes = vM.getHours().toIntOrNull()
            if(intHours == null || intMinutes == null || intHours < 0 || intMinutes < 0){
                vM.onShowErrorPopupChanged(true)
            } else{
                val listEntry = ListEntry(game.id, game.name, vM.getGameScore(),
                    vM.getHours().toInt()*60+vM.getMinutes().toInt(), vM.getGameStatus(),
                    vM.getCoverWithId(game.cover.id)
                        ?.let {
                            imageBuilder(
                                it.imageId,
                                ImageSize.COVER_BIG,
                                ImageType.JPEG
                            )
                        })
                vM.storeListEntry(listEntry)
                vM.onGameFormOpenChanged(false)
            }
        }){
            Text(text = "save")
        }
        if(vM.getShowErrorPopup()){
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {vM.onShowErrorPopupChanged(false)})
                    { Text(text = "OK") }
                },
                title = { Text(text = "wrong time") },
                text = { Text(text = "Please provide a valid time (integers only)") }
            )
        }
    }
}
