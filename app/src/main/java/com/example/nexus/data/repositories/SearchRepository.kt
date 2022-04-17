package com.example.nexus.data.repositories

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.covers
import com.api.igdb.request.games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import proto.Cover
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor() {
    var gameList : MutableState<List<Game>> = mutableStateOf(ArrayList())
    var coverList : MutableState<List<Cover>> = mutableStateOf(ArrayList())
    val searchTerm = mutableStateOf("")
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    suspend fun getGames() = withContext(Dispatchers.IO){
        val apicalypse = APICalypse().fields("*").search(searchTerm.value)
        try{
            val games: List<Game> = IGDBWrapper.games(apicalypse)
            gameList.value = games
        } catch(e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }

    suspend fun getCovers() = withContext(Dispatchers.IO){
        var ids : String = "("
        try {
            for(game in gameList.value) {
                println(game.cover)
                ids += game.id.toString() + ","
            }
            ids = ids.dropLast(1)
            ids += ")"
            println(ids)
            val apicalypse = APICalypse().fields("*").where("game =$ids;")
            val covers : List<Cover> = IGDBWrapper.covers(apicalypse)
            println(covers)
            coverList.value = covers
        } catch (e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }

    fun getCoverWithId(id : Long) : Cover? {
        for (cover in coverList.value){
            if(cover.id == id){
                return cover
            }
        }
        return null
    }

    fun setSearchTerm(term: String){
        this.searchTerm.value = term
    }
}