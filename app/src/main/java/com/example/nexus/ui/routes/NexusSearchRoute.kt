package com.example.nexus.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.example.nexus.ui.components.SearchResultComponent
import com.example.nexus.ui.theme.NexusBlue
import com.example.nexus.ui.theme.NexusGray
import com.example.nexus.ui.theme.NexusLightGray
import com.example.nexus.viewmodels.NexusHomeViewModel
import com.example.nexus.viewmodels.NexusSearchViewModel


@ExperimentalComposeUiApi
@Composable
fun NexusSearchRoute(
    vM: NexusSearchViewModel,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onSearch: () -> Unit? = {vM.onSearchEvent(); keyboardController?.hide()}
) {
    Column {
        TextField(
            value = vM.searchTerm.value, onValueChange = { vM.setSearchTerm(it) },
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
            keyboardActions = KeyboardActions(onDone = { onSearch() }),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = NexusLightGray,
                cursorColor = NexusBlue,
                disabledIndicatorColor = NexusGray,
                focusedIndicatorColor = NexusBlue
            ),
            trailingIcon = {
                IconButton(onClick = { onSearch() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "search"
                    )
                }
            })
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            vM.gameList.value.forEach { game ->
                SearchResultComponent(vM = vM, game = game)
            }
        }
    }

}