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
class GameDetailRepository @Inject constructor() {
    var gameList : Lazy<MutableState<List<Game>>> = lazy { mutableStateOf(ArrayList())}

    init {
        IGDBWrapper.setCredentials("trt599r053jhg3fmjnhehpyzs3xh4w", "tm3zxdsllw4czte0n4mmqkly6crehf")
    }

    suspend fun getGameById(gameId : Long) = withContext(Dispatchers.Default){
        val apicalypse = APICalypse().fields("platforms.abbreviation,cover.image_id," +
                "screenshots.image_id,screenshots.width,screenshots.height," +
                "websites.url,websites.category," +
                "dlcs.name,expanded_games.name,expansions.name,similar_games.name,remakes.name,remasters.name,parent_game.name," +
                "dlcs.cover.image_id,expanded_games.cover.image_id,expansions.cover.image_id,remakes.cover.image_id," +
                "similar_games.cover.image_id,parent_game.cover.image_id," +
                "franchises.games.name, franchises.games.cover.image_id," +
                "genres.name,release_dates.human,release_dates.region,release_dates.platform.abbreviation," +
                "first_release_date,age_ratings.category,age_ratings.rating," +
                "involved_companies.developer, involved_companies.publisher, involved_companies.company.name," +
                "name,rating,rating_count,summary,storyline").where("id = $gameId")

        try{
            val gameRes: List<Game> = IGDBWrapper.games(apicalypse)
            gameList.value.value = gameRes
        } catch(e: RequestException) {
            print("NEXUS API FETCH ERROR:")
            println(e.result)
        }
    }
}
