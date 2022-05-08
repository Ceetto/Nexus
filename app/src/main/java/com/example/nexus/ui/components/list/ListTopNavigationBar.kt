package com.example.nexus.ui.components.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.routes.getAllListCategories
import com.example.nexus.ui.theme.NexusBlue
import com.example.nexus.ui.theme.NexusGray

//scrollable TopNavigationbar
@Composable
fun ListTopNavigationBar(
    selectedCategory: ListCategory,
    onSelectedCategoryChanged: (ListCategory) -> Unit
){

    LazyRow(modifier = Modifier
        .fillMaxWidth(),
        state = rememberLazyListState()
    ){
        items(getAllListCategories()) { it ->
            TopNavigationBox(
                category = it,
                isSelected = it == selectedCategory,
                onSelectedCategoryChanged = {onSelectedCategoryChanged(it)})
        }
    }
}

//items in the TopNavigationBar
@Composable
fun TopNavigationBox(
    category: ListCategory,
    isSelected: Boolean,
    onSelectedCategoryChanged: (ListCategory) -> Unit
) {
    Surface(
        modifier = Modifier.padding(end = 8.dp),
        elevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        color = if(isSelected) NexusBlue else NexusGray
    ){
        Row(modifier = Modifier
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onSelectedCategoryChanged(category)
                }
            )
        ) {
            Text(
                text = category.value,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(5.dp),
                fontSize = 22.sp)
        }

    }
}