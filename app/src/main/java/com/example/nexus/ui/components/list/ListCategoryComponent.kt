package com.example.nexus.ui.components.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.nexus.viewmodels.NexusListViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing

@Composable
fun ListCategoryComponent(
    category: ListCategory,
    vM: NexusListViewModel,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    val games by vM.getCategoryByName(category.value).collectAsState()
    ListCategoryScreen(games, onOpenGameDetails, vM)
}

@Composable
fun ListCategoryScreen(
    games: List<ListEntry>,
    onOpenGameDetails : (gameId: Long) -> Unit,
    vM: NexusListViewModel
){
    Column(){
        SortListComponent(games = games, vM = vM)
        LazyColumn(
        ){
            items(games) {game ->
                ListItem(game = game, onOpenGameDetails)
            }
        }
    }
}

@Composable
fun ListItem(
    game: ListEntry,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    Surface(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onOpenGameDetails(game.gameId) }
    ) { Row(){
            Image(painter = rememberAsyncImagePainter(game.coverUrl), contentDescription = "cover",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(120.dp))

            Column() {
                Text(text = game.title,
                    fontSize = 25.sp)

                Row() {
                    Surface(
                        modifier = Modifier.fillMaxWidth(0.7f)
                    ){
                        val timePlayed = if (game.status == ListCategory.PLANNED.value){
                            ListCategory.PLANNED.value
                        } else {
                            val minutes = game.minutesPlayed.mod(60)
                            val hours = (game.minutesPlayed - minutes)/60
                            "${hours}h ${minutes}m"
                        }
                        ListCategoryColors[game.status]?.let {
                            Text(text = timePlayed,
                                Modifier.padding(end = 30.dp),
                                color = it
                            )
                        }
                    }
                    if (game.score != 0){
                        Icon(imageVector = Icons.Default.Star, contentDescription = "starIcon")
                        Text(text = game.score.toString())
                    } else {
                        Text(text = "No score")
                    }
                }
            }
        }
    }
}

val ListCategoryColors = mapOf(ListCategory.PLAYING.value to Playing,
                                ListCategory.COMPLETED.value to Completed,
                                ListCategory.DROPPED.value to Dropped,
                                ListCategory.PLANNED.value to Planned)

