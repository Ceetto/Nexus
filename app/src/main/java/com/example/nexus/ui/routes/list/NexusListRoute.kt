package com.example.nexus.ui.routes.list

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nexus.viewmodels.NexusListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.ui.graphics.Color

@ExperimentalAnimationApi
@Composable
fun NexusListRoute(
    vM: NexusListViewModel
){
    Scaffold(topBar = {
        TopNavigationBar(vM.selectedCategory.value, vM::onSelectedCategoryChanged)
    }){
        ListCategoryRoute(category = vM.selectedCategory.value)
    }
}

@Composable
fun TopNavigationBar(
    selectedCategory: ListCategory,
    onSelectedCategoryChanged: (ListCategory) -> Unit
){

    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
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
        color = if(isSelected) Color.LightGray else MaterialTheme.colors.secondary
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
                color= MaterialTheme.colors.primary,
                modifier = Modifier.padding(5.dp),
                fontSize = 25.sp)
        }

    }
}

//private class ListNavigationItem(
//    val tabValue: String,
//    val value: ListCategory
//)
//
//private val ListNavigationItems = listOf(
//    ListNavigationItem("All", ListCategory.ALL),
//    ListNavigationItem("Playing", ListCategory.PLAYING),
//    ListNavigationItem("Completed", ListCategory.COMPLETED),
//    ListNavigationItem("Planned", ListCategory.PLANNED),
//    ListNavigationItem("Dropped", ListCategory.DROPPED),
//)

enum class ListCategory(val value: String){
    ALL("All"),
    PLAYING("Playing"),
    COMPLETED("Completed"),
    PLANNED("Planned"),
    DROPPED("Dropped")
}

fun getAllListCategories(): List<ListCategory> =
    listOf(ListCategory.ALL, ListCategory.PLAYING, ListCategory.COMPLETED,
        ListCategory.PLANNED, ListCategory.DROPPED
    )