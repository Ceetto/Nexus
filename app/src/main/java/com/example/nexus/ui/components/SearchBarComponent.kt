package com.example.nexus.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.nexus.viewmodels.games.NexusSearchViewModel

@ExperimentalComposeUiApi
@Composable
fun SearchBarComponent(
    vM: NexusSearchViewModel,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onSearch: () -> Unit? = {vM.onSearchEvent();},
) {
    TextField(
        value = vM.getSearchTerm(), onValueChange = { vM.setSearchTerm(it); onSearch() },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
//                .padding(5.dp, 1.dp)
        ,
        placeholder = { Text("search games") },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { keyboardController?.hide() }),
        leadingIcon = {
            IconButton(onClick = {
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            }
        },
        trailingIcon = {
            if(vM.getSearchTerm().isNotEmpty()){
                IconButton(onClick = {
                    vM.setSearchTerm("")
                    vM.onSearchEvent()}) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "remove"
                    )
                }
            }
        }
    )
}