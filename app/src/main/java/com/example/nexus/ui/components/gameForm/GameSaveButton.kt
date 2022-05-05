package com.example.nexus.ui.components.gameForm

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.games.NexusGameDetailViewModel

@Composable
fun GameSaveButton(
    vM: NexusGameDetailViewModel,
    focusManager: FocusManager){
    Button(modifier = Modifier.padding(10.dp),
        onClick = {
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
}