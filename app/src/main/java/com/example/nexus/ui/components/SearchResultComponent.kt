package com.example.nexus.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.viewmodels.games.NexusSearchViewModel
import proto.Game
import kotlin.math.roundToInt

@Composable
fun SearchResultComponent(
    vM: NexusSearchViewModel,
    game: Game,
    onClick: (gameId: Long) -> Unit
){
    Row(modifier = Modifier
        .padding(bottom = 5.dp)
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(onTap = { onClick(game.id) })
        }
    ){
        Image(
            painter = rememberAsyncImagePainter(vM.getCoverWithId(game.cover.id)
                ?.let { imageBuilder(it.imageId, ImageSize.COVER_BIG, ImageType.JPEG) }),
            contentDescription = "cover image",
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(120.dp)
        )
        Column {
            Text(text = game.name)
            if (game.ratingCount > 0) {
                Text(text = "Score: " + ((game.rating*10).roundToInt().toDouble()/100).toString())
            } else {
                Text(text = "Score: N/A")
            }
        }
    }
}