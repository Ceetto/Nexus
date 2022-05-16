package com.example.nexus.viewmodels.profile

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.repositories.FriendsRepository
import com.example.nexus.data.repositories.NotificationsRepository
import com.example.nexus.data.repositories.ProfileRepository
import com.example.nexus.data.repositories.list.FriendListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NexusFriendProfileViewModel @Inject constructor(private val profileRepo: ProfileRepository,
                                                      private  val listRepo: FriendListRepository,
                                                      private val friendRepo: FriendsRepository,
                                                      private val notifRepo: NotificationsRepository,
                                                      savedStateHandle: SavedStateHandle,
) : ViewModel(){

    private val friendId: String = savedStateHandle["userId"]!!

    fun getUser() : User {
        return profileRepo.getFriend()
    }

    fun getUserId():String{
        return friendId
    }

    fun getCategoryByName(category: String): StateFlow<List<ListEntry>> {
        listRepo.setFriendId(friendId)
        return listRepo.getCategoryByName(category).stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }


    fun getFavourites(): StateFlow<List<ListEntry>> {
        return listRepo.favorites.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
    }

    fun getFriendsData() : StateFlow<List<Friend>> {
        return friendRepo.getFriendsData()
    }

    fun getSearchResults() : StateFlow<List<Friend>> {
        return friendRepo.getUserMatches()
    }

    fun isFriend() : Boolean{
        for (f in getFriendsData().value){
            if (f.userId == friendId){
                return true
            }
        }
        return false
    }

    fun getNewFriend(): Friend {
        for (f in getSearchResults().value){
            println(f.username)
            if (f.userId == friendId){
                return f
            }
        }
        return Friend("", "", "", "")
    }

    fun getFriend() : Friend{
        for (f in getFriendsData().value){
            if (f.userId == friendId){
                return f
            }
        }
        return Friend("", "", "", "")
    }



    fun sendFriendRequest(f: Friend, user: User){
        println("sent friend request")
        println("to: ${f.username}")
        notifRepo.sendFriendRequest(f, user)
    }

    fun removeFriend(f: Friend) {
        friendRepo.removeFriend(f)
    }

    fun getCurrentUser():User{
        return profileRepo.getUser()
    }

}