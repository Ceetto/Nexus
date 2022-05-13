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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import proto.Game

//@ExperimentalComposeUiApi
@Composable
fun SearchBarComponent(
    placeholder: String,
    onSearch: () -> Unit?,
    getSearchTerm: String,
    setSearchTerm: (String) -> Unit,
    setSearched: (Boolean) -> Unit,
    onCancel: () -> Unit
) {
    TextField(
        value = getSearchTerm,
        onValueChange = {
            if(!it.contains("\n")){
                setSearchTerm(it);
                if(it.isEmpty()){
                    setSearched(false)
                }
            }
                        },
        singleLine = true,
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
        ,
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = { onSearch() }),
        leadingIcon = {
            IconButton(onClick = {
                onSearch()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "search"
                )
            }
        },
        trailingIcon = {
            if(getSearchTerm.isNotEmpty()){
                IconButton(onClick = {
                    onCancel()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "remove"
                    )
                }
            }
        }
    )
}