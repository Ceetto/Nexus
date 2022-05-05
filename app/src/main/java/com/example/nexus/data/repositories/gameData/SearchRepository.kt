package com.example.nexus.data.repositories.gameData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor()
{
    var gameList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList())}
    val searchTerm = mutableStateOf("")
    var searching: Lazy<MutableState<Boolean>> = lazy { mutableStateOf(false)}
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    fun getGames(){
        if(searchTerm.value.isNotEmpty()){
            searching.value.value = true
            val apicalypse = APICalypse().fields("cover.image_id,name, rating, rating_count")
                .where("name ~ *\"${searchTerm.value}\"* | franchise.name ~ *\"${searchTerm.value}\"* "
                        + "| alternative_names.name ~ *\"${searchTerm.value}\"* | parent_game.name ~ *\"${searchTerm.value}\"* | remakes.name ~ *\"${searchTerm.value}\"*"
                )
            gameList.value.value = kotlin.collections.emptyList()
            try{
                gameList.value.value = IGDBWrapper.games(apicalypse)
                searching.value.value = false
            } catch(e: RequestException) {
                print("NEXUS API FETCH ERROR: ")
                println(e.message)
                println(e.result)
                searching.value.value = false
            }
        }

    }

    fun emptyList(){
        gameList.value.value = kotlin.collections.emptyList()
    }

    fun setSearchTerm(term: String){
        this.searchTerm.value = term
    }
}