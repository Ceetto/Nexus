package com.example.nexus.viewmodels.games

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.compose.rememberAsyncImagePainter
import com.api.igdb.utils.ImageSize
import com.api.igdb.utils.ImageType
import com.api.igdb.utils.imageBuilder
import com.example.nexus.R
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.data.repositories.gameData.GameDetailRepository
import com.example.nexus.ui.routes.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import proto.AgeRatingCategoryEnum
import proto.AgeRatingRatingEnum
import proto.Game
import proto.Website
import javax.inject.Inject

@HiltViewModel
class NexusGameDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repo: GameDetailRepository,
    private val listRepository: ListRepository
) : ViewModel(){
    private var gameList = repo.gameList
    private val gameId: Long = savedStateHandle["gameId"]!!
    private val isRefreshing = mutableStateOf(false)

    private var gameFormOpen = mutableStateOf(false)
    private var ageVerifOpen = mutableStateOf(false)
    private var showErrorPopup = mutableStateOf(false)
    private val currentListEntry = mutableStateOf(ListEntry(0, "", 0,
    0, "", "", false, 0))
    private val hours = mutableStateOf("0")
    private val minutes = mutableStateOf("0")
    private val editOrAddGames = mutableStateOf(GameFormButton.ADD.value)
    private val showDeleteWarning = mutableStateOf(false)
    private val icon = mutableStateOf(Icons.Outlined.StarBorder)
    private val favoriteToggled = mutableStateOf(false)
    private val prefilledGame = mutableStateOf(false)

    fun isPrefilledGame(): Boolean {
        return prefilledGame.value
    }

    fun onGetGameEvent(){

        if(gameList.value.value.isNotEmpty() && (gameList.value.value[0].id != gameId || isAdult())){
            gameList.value.value = emptyList()
        }
        viewModelScope.launch {
            try{
                isRefreshing.value = true
                repo.getGameById(gameId)
                isRefreshing.value = false
                if(isAdult()){
                    onAgeVerifOpenChange(true)
                }
            } catch(e: Exception){

            }
        }
    }

    fun isRefreshing(): Boolean{
        return isRefreshing.value
    }

    private fun isAdult(): Boolean{
        if(gameList.value.value.isNotEmpty()){
            val game = gameList.value.value[0]
            for(rating in game.ageRatingsList){
                if(rating.category == AgeRatingCategoryEnum.PEGI && rating.rating == AgeRatingRatingEnum.EIGHTEEN){
                    return true
                }
            }
        }
        return false
    }

    fun ageVerifOpen(): Boolean{
        return ageVerifOpen.value
    }

    fun onAgeVerifOpenChange(v : Boolean){
        ageVerifOpen.value = v
    }

    fun getIcon(): ImageVector {
        return icon.value
    }

    fun setIcon(iv: ImageVector){
        icon.value = iv
    }

    fun toggleIcon(){
        if (icon.value == Icons.Outlined.StarBorder){
            icon.value = Icons.Outlined.Star
            favoriteToggled.value = true
        } else {
            icon.value = Icons.Outlined.StarBorder
            favoriteToggled.value = false
        }
    }

    fun getFavoriteToggled(): Boolean {
        return favoriteToggled.value
    }

    fun getEditOrAddGames(): String {
        return editOrAddGames.value
    }

    fun setEditOrAddGames(text: String){
        editOrAddGames.value = text
    }

    fun getGameList(): List<Game> {
        return gameList.value.value
    }

    fun setListEntry(entry: ListEntry){
        currentListEntry.value = entry
    }

    fun getListEntry(): ListEntry {
        return currentListEntry.value
    }

    fun setGameScore(score: Int){
        currentListEntry.value.score = score
    }

    fun getGameScore(): Int {
        return currentListEntry.value.score
    }

    fun setGameStatus(status: String){
        currentListEntry.value.status = status
    }

    fun getGameStatus(): String {
        return currentListEntry.value.status
    }

    fun setHours(hours: String){
        this.hours.value = hours
    }

    fun getHours(): String {
        return hours.value
    }

    fun setMinutes(minutes: String){
        this.minutes.value = minutes
    }

    fun getMinutes(): String {
        return minutes.value
    }

    fun setFavorite(boolean: Boolean){
        currentListEntry.value.favorited = boolean
    }

    fun setCurrentListEntryMinutes(minutes: Int){
        currentListEntry.value.minutesPlayed = minutes
    }

    fun getGameFormOpen(): Boolean {
        return gameFormOpen.value
    }

    fun getShowErrorPopup(): Boolean {
        return showErrorPopup.value
    }

    fun onGameFormOpenChanged(boolean: Boolean){
        gameFormOpen.value = boolean
    }

    fun onShowErrorPopupChanged(boolean: Boolean){
        showErrorPopup.value = boolean
    }

    fun onShowDeleteWarningChanged(boolean: Boolean){
        showDeleteWarning.value = boolean
    }

    fun getShowDeleteWarning(): Boolean {
        return showDeleteWarning.value
    }

    fun prefillGame(game: Game, games: List<ListEntry>){
        var i = 0
        while (i < games.size && !prefilledGame.value){
            if(games[i].gameId == game.id){
                setListEntry(games[i])
                setMinutes(getListEntry().minutesPlayed.mod(60).toString())
                setHours(((getListEntry().minutesPlayed - getMinutes().toInt())/60).toString())
                setEditOrAddGames(GameFormButton.EDIT.value)
                if(games[i].favorited){
                    setIcon(Icons.Outlined.Star)
                } else {
                    setIcon(Icons.Outlined.StarBorder)
                }
                prefilledGame.value = true
            }
            i++
        }

        //fills in the current ListEntry in case the game was not found in your list
        if (!prefilledGame.value){
            setListEntry(ListEntry(game.id, game.name, 0, 0, ListCategory.PLAYING.value,
                game.cover?.let {
                    imageBuilder(
                        it.imageId,
                        ImageSize.COVER_BIG,
                        ImageType.JPEG
                    )
                }, false, game.firstReleaseDate.seconds))
            setEditOrAddGames(GameFormButton.ADD.value)
        }
    }


//    fun getCoverWithId(id: Long) = repo.getCoverWithId(id)
//    fun getPlatforms(ids: MutableList<Platform>) = repo.getPlatforms(ids)

    fun storeListEntry(entry: ListEntry) = viewModelScope.launch { listRepository.storeListEntry(entry) }

    fun deleteListEntry(entry: ListEntry) = viewModelScope.launch { listRepository.deleteListEntry(entry) }

    val allGames: StateFlow<List<ListEntry>> by lazy {
        listRepository.getAllGames().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    enum class GameFormButton(val value: String){
        ADD("Add game"),
        EDIT("Edit game")
    }

    private val linkIconsMap = hashMapOf(
        "official" to R.drawable.official,
        "wikia" to R.drawable.wikia,
        "wikipedia" to R.drawable.wikipedia,
        "facebook" to R.drawable.facebook,
        "twitter" to R.drawable.twitter,
        "twitch" to R.drawable.twitch,
        "instagram" to R.drawable.instagram,
        "youtube" to R.drawable.youtube,
        "iphone" to R.drawable.apple,
        "ipad" to R.drawable.apple,
        "android" to R.drawable.android,
        "steam" to R.drawable.steam,
        "reddit" to R.drawable.reddit,
        "itch" to R.drawable.itchio,
        "epicgames" to R.drawable.epicgames,
        "gog" to R.drawable.gog,
        "discord" to R.drawable.discord
    )

    fun getLinkIcon(s : String): Int? {
        return linkIconsMap[s]
    }
}