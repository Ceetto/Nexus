package com.example.nexus.viewmodels.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.list.FriendListRepository
import com.example.nexus.ui.routes.lists.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusFriendListViewModel @Inject constructor(
    private val repo: FriendListRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel(){

    private val friendId: String = savedStateHandle["userId"]!!

    fun toggleDescendingOrAscendingIcon() = repo.toggleDescendingOrAscendingIcon()

    fun getDescendingOrAscendingIcon() = repo.getDescendingOrAscendingIcon()

    fun setSortOption(option : String) = repo.setSortOption(option)

    fun getSortOption() = repo.getSortOption()

    fun onSelectedCategoryChanged(category: ListCategory) = repo.onSelectedCategoryChanged(category)

    fun getSelectedCategory() = repo.getSelectedCategory()

    fun getCategoryByName(category: String): StateFlow<List<ListEntry>> {
        val games: StateFlow<List<ListEntry>> by lazy {
            when(category){
                ListCategory.PLAYING.value -> repo.getPlaying().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.COMPLETED.value -> repo.getCompleted().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.PLANNED.value -> repo.getPlanned().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.DROPPED.value -> repo.getDropped().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                else -> {repo.getAllGames().stateIn(viewModelScope, SharingStarted.Lazily, emptyList())}
            }
        }
        return games
    }

    fun onGetListEvent(){
        setFriendId()
    }
    private fun setFriendId() = repo.setFriendId(friendId)

}