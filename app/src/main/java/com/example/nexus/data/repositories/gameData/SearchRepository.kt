package com.example.nexus.data.repositories.gameData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.apicalypse.Sort
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.covers
import com.api.igdb.request.games
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Cover
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor()
{
    var gameList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList())}
    val searchTerm = mutableStateOf("")
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    fun getGames(){
        val apicalypse = APICalypse().fields("cover.*,franchise.*,alternative_names.*,*")
            .where("name ~ *\"${searchTerm.value}\"* | franchise.name ~ *\"${searchTerm.value}\"* | alternative_names.name ~ *\"${searchTerm.value}\"*")
        gameList.value.value = emptyList()
        try{
            gameList.value.value = IGDBWrapper.games(apicalypse)
        } catch(e: RequestException) {
            print("NEXUS API FETCH ERROR: ")
            println(e.message)
            println(e.result)
        }
    }

    fun setSearchTerm(term: String){
        this.searchTerm.value = term
    }
}