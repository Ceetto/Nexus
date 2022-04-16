package com.example.nexus.ui.routes.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.data.db.ListEntity
import com.example.nexus.viewmodels.NexusListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.ui.unit.sp

@Composable
fun ListCategoryRoute(
    category: ListCategory,
    vM: NexusListViewModel
) {
    val games by vM.allGames.collectAsState()
    Text(
        text = category.value
    )

    ListCategoryScreen(games = games)
}

@Composable
fun ListCategoryScreen(
    games: List<ListEntity>
){
    LazyColumn(
        modifier = Modifier.padding(16.dp)
    ){
        items(games) {game ->
            ListItem(game = game)
        }
    }
}

@Composable
fun ListItem(
    game: ListEntity
){
    Surface(
        modifier = Modifier.padding(end = 8.dp)
    ){
        Text(text = game.title,
            fontSize = 20.sp)
    }

}

