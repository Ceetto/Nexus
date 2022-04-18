package com.example.nexus.data.repositories.gameData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.covers
import com.api.igdb.request.games
import com.api.igdb.request.platforms
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Cover
import proto.Game
import proto.Platform
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDetailRepository @Inject constructor(private val repo: SearchRepository) {
    var gameList : MutableState<List<Game>> = mutableStateOf(ArrayList())
    var coverList : MutableState<List<Cover>> = mutableStateOf(ArrayList())
    var platformList : MutableState<List<Platform>> = mutableStateOf(ArrayList())
    var gamePlatforms : MutableState<List<String>> = mutableStateOf(ArrayList())
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    suspend fun getCovers() = withContext(Dispatchers.IO){
        var ids = "("
        try {
            for(game in gameList.value) {
                ids += game.id.toString() + ","
            }
            ids = ids.dropLast(1)
            ids += ")"
            val apicalypse = APICalypse().fields("*").where("game =$ids;")
            val covers : List<Cover> = IGDBWrapper.covers(apicalypse)
            coverList.value = covers
        } catch (e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }

    suspend fun getPlatforms() = withContext(Dispatchers.IO){
        try{
            val apiCalypse = APICalypse().fields("*")
            platformList.value = IGDBWrapper.platforms(apiCalypse)
        } catch (e : RequestException){
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }

    suspend fun getGameById(gameId : Long) = withContext(Dispatchers.IO){
        val apicalypse = APICalypse().fields("*").where("id = $gameId")
        try{
            val gameRes: List<Game> = IGDBWrapper.games(apicalypse)
            gameList.value = gameRes
        } catch(e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }

    fun getCoverWithId(id : Long) : Cover? {
        for (cover in coverList.value){
            if(cover.id == id){
                println(cover)
                return cover
            }
        }
        return null
    }

    fun getPlatforms(ids: MutableList<Platform>){
        var platforms : MutableList<String> = ArrayList()
        for(id in ids){
            for(platform in platformList.value){
                if(platform.id == id.id){
                    platforms.add(platform.name)
                }
            }
        }
        gamePlatforms.value = platforms
    }
}