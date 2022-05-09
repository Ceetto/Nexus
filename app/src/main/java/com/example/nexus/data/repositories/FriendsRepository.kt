package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseFriendsDao
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendsRepository @Inject constructor(
    private val firebaseFriendsDao: FirebaseFriendsDao
) {
    fun getFriends(): Flow<List<String>>{
        return firebaseFriendsDao.getFriends()
    }

    fun storeFriend(f: String) = firebaseFriendsDao.storeFriend(f)

    fun updateUser() = firebaseFriendsDao.updateUser()
}