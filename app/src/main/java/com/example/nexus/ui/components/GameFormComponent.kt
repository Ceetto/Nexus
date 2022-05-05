package com.example.nexus.ui.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel
import proto.Game

//TODO data op voorhand invullen bij games die al in de lijst zitten
//TODO updaten en verwijderen

@Composable
fun GameFormComponent(
    vM: NexusGameDetailViewModel
){
    val focusManager = LocalFocusManager.current

    Column(verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .pointerInput(Unit) {
                    detectTapGestures(onTap = {
                        focusManager.clearFocus()
                    })
                }
                .fillMaxSize()) {
        Row(){
            IconButton(onClick = { vM.onGameFormOpenChanged(false)
                                    focusManager.clearFocus()}) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close game form",
                )
            }
        }

        //score input
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Your score: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf(vM.getGameScore().toString())}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text, color=MaterialTheme.colors.onBackground)
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
                        Text(text = score.toString())
                    }
                }
            }
        }

        //status input
        Row(modifier = Modifier.padding(5.dp)){
            Text(text = "Status: ")
            var expanded by remember { mutableStateOf(false) }
            var text by remember { mutableStateOf(vM.getGameStatus())}
            OutlinedButton(onClick = { expanded = !expanded }) {
                Text(text = text, color=MaterialTheme.colors.onBackground)
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

//        //hours input
//        Row(modifier = Modifier
//            .padding(5.dp)
//            .pointerInput(Unit) {
//                detectTapGestures(onTap = {
//                    focusManager.clearFocus()
//                })
//            }){
//            Text(text = "Hours played: ")
//            TextField(
//                value = vM.getHours(),
//                onValueChange = { value ->
//                        vM.setHours(value)
//                    },
//                keyboardOptions = KeyboardOptions.Default.copy(
//                    keyboardType = KeyboardType.Number,
//                    imeAction = ImeAction.Done
//                ),
//                keyboardActions = KeyboardActions(onDone = {
//                    focusManager.clearFocus()
//                })
//            )
//        }

        TimeInput(focusManager = focusManager, text = "Hours played: ", getTime = { vM.getHours() },
            setTime = {hours -> vM.setHours(hours)})

        //minutes input
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
                vM.setCurrentListEntryMinutes(vM.getHours().toInt()*60+vM.getMinutes().toInt())
                vM.storeListEntry(vM.getListEntry())
                vM.onGameFormOpenChanged(false)
                focusManager.clearFocus()
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

@Composable
fun TimeInput(
    focusManager: FocusManager,
    text: String,
    getTime: () -> String,
    setTime: (time: String) -> Unit){
    Row(modifier = Modifier
        .padding(5.dp)
        .pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }){
        Text(text = text)
        TextField(
            value = getTime(),
            onValueChange = { value ->
                setTime(value)
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
}
