package com.example.nexus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import proto.Game

@Composable
fun HorizontalGameDisplayComponent(
    game: Game,
    onClick: (gameId: Long) -> Unit,
    focusManager: FocusManager
){
    Column(modifier = Modifier
        .pointerInput(Unit) {
            detectTapGestures(onTap = { onClick(game.id)
                focusManager.clearFocus()})
        }
        .width(127.dp)
    ){
        Image(
            painter = rememberAsyncImagePainter(game.cover
                ?.let { imageBuilder(it.imageId, ImageSize.COVER_BIG, ImageType.JPEG) }),
            contentDescription = "cover image",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .size(127.dp, 150.dp)
                .padding(end = 5.dp)
        )
        Text(game.name)
    }
}

@Composable
fun HorizontalGamesListingComponent(
    list: List<Game>,
    onOpenGameDetails : (gameId: Long) -> Unit,
    focusManager: FocusManager,
    title: String,
    isFetching: Boolean
){
    Column(
        Modifier.padding(start = 5.dp, top = 5.dp, bottom = 10.dp)
    ){
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        if(!isFetching){
            Row(Modifier.horizontalScroll(rememberScrollState())){
                list.forEach { game -> Row{
                    HorizontalGameDisplayComponent(game, onOpenGameDetails, focusManager)
                } }
            }

        } else {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.Start
            ){
                CircularProgressIndicator()
            }
        }
    }
}
