package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.components.list.ListCategoryColors
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

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
        Row(horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth()) {
            Text(text = vM.getListEntry().title, fontSize = 20.sp)
        }

        //score input
        Row(modifier = Modifier.padding(5.dp)) {
            Text(text = "Your score: ")
            var expanded by remember { mutableStateOf(false) }
            val scoreText = if (vM.getGameScore() == 0){
                "No score"
            } else {
                vM.getGameScore().toString()
            }
            var text by remember { mutableStateOf(scoreText)}
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
                ListCategoryColors[text]?.let { Text(text = text, color= it) }
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                listOf(ListCategory.PLAYING, ListCategory.PLANNED, ListCategory.COMPLETED,
                ListCategory.DROPPED).forEach { status ->
                    DropdownMenuItem(onClick = { expanded = false
                                                text = status.value
                                                vM.setGameStatus(status.value)}) {
                        ListCategoryColors[status.value]
                            ?.let { Text(text = status.value, color = it) }
                    }
                }
            }
        }

        //hours input
        TimeInput(focusManager = focusManager, text = "Hours played: ", getTime = { vM.getHours() },
            setTime = {hours -> vM.setHours(hours)})

        //minutes input
        TimeInput(focusManager = focusManager, text = "Minutes played: ", getTime = { vM.getMinutes() },
            setTime = {minutes -> vM.setMinutes(minutes)})


        Row(modifier = Modifier.padding(5.dp)){
            GameSaveButton(focusManager = focusManager, vM.getHours(), vM.getMinutes(),
                {b:Boolean -> vM.onShowErrorPopupChanged(b)},
                {m:Int -> vM.setCurrentListEntryMinutes(m)},
                {l:ListEntry -> vM.storeListEntry(l)},
                vM.getListEntry(),
                {b:Boolean -> vM.onGameFormOpenChanged(b)}
            )

            GameDeleteButton(vM.getEditOrAddGames()) { b: Boolean -> vM.onShowDeleteWarningChanged(b) }
        }

        if(vM.getShowErrorPopup()){
            AlertDialog(
                onDismissRequest = { },
                confirmButton = {
                    TextButton(onClick = {vM.onShowErrorPopupChanged(false)})
                    { Text(text = "OK", color=MaterialTheme.colors.onBackground) }
                },
                title = { Text(text = "wrong time") },
                text = { Text(text = "Please provide a valid time (integers only)") }
            )
        }

        if(vM.getShowDeleteWarning()){
            AlertDialog(onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    vM.deleteListEntry(vM.getListEntry())
                    vM.setEditOrAddGames(NexusGameDetailViewModel.GameFormButton.ADD.value)
                    vM.onGameFormOpenChanged(false)
                    focusManager.clearFocus()
                    vM.onShowDeleteWarningChanged(false)}) {
                    Text(text = "Confirm", color=MaterialTheme.colors.onBackground)
                }
            },
                dismissButton = {
                    TextButton(onClick = {
                    vM.onShowDeleteWarningChanged(false) }) {
                    Text(text = "Cancel", color=MaterialTheme.colors.onBackground)
                }},
            text = { Text(text = "Are you sure you want to delete this game from your list?")}
            )
        }
    }
}
