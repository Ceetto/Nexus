package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

@Composable
fun GameSaveButton(
    focusManager: FocusManager,
    getHours: String,
    getMinutes: String,
    onShowErrorPopupChanged: (Boolean) -> Unit,
    setCurrentListEntryMinutes: (Int) -> Unit,
    storeListEntry: (ListEntry) -> Unit,
    getListEntry : ListEntry,
    onGameFormOpenChanged : (Boolean) -> Unit,
){
    Button(modifier = Modifier.padding(10.dp),
        onClick = {
            val intHours = getHours.toIntOrNull()
            val intMinutes = getMinutes.toIntOrNull()
            if(intHours == null || intMinutes == null || intHours < 0 || intMinutes < 0){
                onShowErrorPopupChanged(true)
            } else{
                setCurrentListEntryMinutes(getHours.toInt()*60+getMinutes.toInt())
                storeListEntry(getListEntry)
                onGameFormOpenChanged(false)
                focusManager.clearFocus()
            }
        }){
        Text(text = "save")
    }
}