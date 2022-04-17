package com.example.nexus.ui.routes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.covers
import com.api.igdb.request.games
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Cover
import proto.Game
import java.lang.Exception
import javax.inject.Inject


@ExperimentalComposeUiApi
@Composable
fun NexusHomeRoute(
    vM: tmpVm,
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current,
    onSearch: () -> Unit? = {vM.onSearchEvent(); keyboardController?.hide()}
){
    Column{
        TextField(
            value = vM.searchTerm.value, onValueChange = {vM.setSearchTerm(it)},
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp, 1.dp),
            placeholder = {Text("search games")},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {onSearch()}),
            trailingIcon = {
                IconButton(onClick = {onSearch()}) {
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
                Column() {
                    Text(text = game.id.toString())
                    Text(text = game.name)
                    Text(text = game.rating.toString() + " based on: " + game.ratingCount)
//                    Text(text = "cover url: " + imageBuilder(game.cover!!.imageId.toString(), ImageSize.SCREENSHOT_HUGE, ImageType.PNG))
//                    Image(
//                        painter = rememberAsyncImagePainter(imageBuilder(game.cover.imageId.toString(), ImageSize.SCREENSHOT_HUGE, ImageType.JPEG)),
//                        contentDescription = "cover image of: " + game.game.name,
//                        modifier = Modifier.size(32.dp)
//                    )
                }
            }
            vM.coverList.value.forEach{ cover ->
                Image(
                        painter = rememberAsyncImagePainter(imageBuilder(cover.imageId.toString(), ImageSize.SCREENSHOT_HUGE, ImageType.JPEG)),
                        contentDescription = "cover image",
                        modifier = Modifier.size(64.dp)
                    )
            }
        }
    }

}



@HiltViewModel
class tmpVm @Inject constructor() : ViewModel(){
    var gameList : MutableState<List<Game>> = mutableStateOf(ArrayList())
    var coverList : MutableState<List<Cover>> = mutableStateOf(ArrayList())
    val searchTerm = mutableStateOf("")
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    fun onSearchEvent(){
        viewModelScope.launch {
            try{
                getGames()
            } catch(e: Exception){

            }
        }
    }

    suspend fun getGames() = withContext(Dispatchers.IO){
        val apicalypse = APICalypse().fields("*").limit(20).search(searchTerm.value)
        try{
            val games: List<Game> = IGDBWrapper.games(apicalypse)
            gameList.value = games
            getCovers()
        } catch(e: RequestException) {
            // Do something or error
        }
    }

    suspend fun getCovers() = withContext(Dispatchers.IO){
        val covers : MutableList<Cover> = ArrayList()
        for(game in gameList.value) {
            val apicalypse = APICalypse().fields("*").where("game = ${game.id}")
            try {
                covers.add(IGDBWrapper.covers(apicalypse)[0])
            } catch (e: RequestException) {
                print("NEXUS API FETCH ERROR:")
                println(e.result)
            }
        }
        coverList.value = covers
    }

    fun setSearchTerm(term: String){
        this.searchTerm.value = term
    }
}

data class GameAndCover(
    val game: Game,
    val cover: Cover?
)