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
import com.example.nexus.data.web.ListEntry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListCategoryRoute(
    category: ListCategory,
    vM: NexusListViewModel
) {
    Text(
        text = category.value
    )
    //val games by vM.getCategory(category.value).collectAsState()
    //val games by vM.allGames.collectAsState()
    //ListCategoryScreen(games)

    when(category){
        ListCategory.ALL -> AllScreen(vM)
        ListCategory.PLAYING -> PlayingScreen(vM)
        ListCategory.COMPLETED -> CompletedScreen(vM)
        ListCategory.PLANNED -> PlannedScreen(vM)
        ListCategory.DROPPED -> DroppedScreen(vM)
    }
}

@Composable
fun AllScreen(
    vM: NexusListViewModel
){
    val games by vM.allGames.collectAsState()
    ListColumn(games)
}

@Composable
fun PlayingScreen(
    vM: NexusListViewModel
){
    val games by vM.playing.collectAsState()
    ListColumn(games)
}

@Composable
fun CompletedScreen(
    vM: NexusListViewModel
){
    val games by vM.completed.collectAsState()
    ListColumn(games)
}

@Composable
fun PlannedScreen(
    vM: NexusListViewModel
){
    val games by vM.planned.collectAsState()
    ListColumn(games)
}

@Composable
fun DroppedScreen(
    vM: NexusListViewModel
){
    val games by vM.dropped.collectAsState()
    ListColumn(games)
}


@Composable
fun ListCategoryScreen(
    games: List<ListEntity>
){
    ListColumn(games)
}

@Composable
fun ListColumn(
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

