package com.example.nexus.data.repositories.gameData

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.api.igdb.apicalypse.APICalypse
import com.api.igdb.exceptions.RequestException
import com.api.igdb.request.IGDBWrapper
import com.api.igdb.request.games
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import proto.Game
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameDetailRepository @Inject constructor(private val repo: SearchRepository) {
    var gameList : MutableState<List<Game>> = mutableStateOf(ArrayList())
    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    suspend fun getGameById(gameId : Long) = withContext(Dispatchers.IO){
        val apicalypse = APICalypse().fields("platforms.*,cover.*,screenshots.*,genres.*,*").where("id = $gameId")
        try{
            val gameRes: List<Game> = IGDBWrapper.games(apicalypse)
            gameList.value = gameRes
        } catch(e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }
}