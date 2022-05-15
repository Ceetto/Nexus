package com.example.nexus.viewmodels.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.repositories.ListRepository
import com.example.nexus.ui.routes.ListCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusFriendListViewModel @Inject constructor(
    private val repo: ListRepository,
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
                ListCategory.PLAYING.value -> repo.removeStatusSortOption(repo.getFriendGames().map { it.filter { entry: ListEntry -> entry.status == ListCategory.PLAYING.value } }).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.COMPLETED.value -> repo.removeStatusSortOption(repo.getFriendGames().map { it.filter { entry: ListEntry -> entry.status == ListCategory.COMPLETED.value } }).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.PLANNED.value -> repo.removeStatusSortOption(repo.getFriendGames().map { it.filter { entry: ListEntry -> entry.status == ListCategory.PLANNED.value } }).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                ListCategory.DROPPED.value -> repo.removeStatusSortOption(repo.getFriendGames().map { it.filter { entry: ListEntry -> entry.status == ListCategory.DROPPED.value } }).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
                else -> {repo.sortGames(repo.getFriendGames()).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())}
            }
        }
        return games
    }

    fun setFriendId(s: String) = repo.setFriendId(s)

}