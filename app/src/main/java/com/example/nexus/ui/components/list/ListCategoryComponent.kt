package com.example.nexus.ui.components.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.routes.ListCategory
import com.example.nexus.ui.routes.ListCategoryColors
import com.example.nexus.ui.theme.Completed
import com.example.nexus.ui.theme.Dropped
import com.example.nexus.ui.theme.Planned
import com.example.nexus.ui.theme.Playing
import com.example.nexus.viewmodels.NexusListViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListCategoryComponent(
    category: ListCategory,
    getCategoryByName: (category: String) -> StateFlow<List<ListEntry>>,
    toggleDescendingOrAscendingIcon: () -> Unit,
    getDescendingOrAscendingIcon:  () -> ImageVector,
    getSelectedCategory: () -> ListCategory,
    setSortOption: (String) -> Unit,
    getSortOption: () -> String,
    onOpenGameDetails : (gameId: Long) -> Unit
) {
    val games by getCategoryByName(category.value).collectAsState()
    ListCategoryScreen(games, onOpenGameDetails, toggleDescendingOrAscendingIcon,
        getDescendingOrAscendingIcon, getSelectedCategory, setSortOption, getSortOption)
}

@Composable
fun ListCategoryScreen(
    games: List<ListEntry>,
    onOpenGameDetails : (gameId: Long) -> Unit,
    toggleDescendingOrAscendingIcon: () -> Unit,
    getDescendingOrAscendingIcon: () -> ImageVector,
    getSelectedCategory: () -> ListCategory,
    setSortOption: (String) -> Unit,
    getSortOption: () -> String
){
    Column(){
        SortListComponent(
            games = games,
            toggleDescendingOrAscendingIcon = toggleDescendingOrAscendingIcon,
            getDescendingOrAscendingIcon = getDescendingOrAscendingIcon,
            getSelectedCategory = getSelectedCategory,
            setSortOption = setSortOption,
            getSortOption = getSortOption)
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
                    fontSize = 20.sp)

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

