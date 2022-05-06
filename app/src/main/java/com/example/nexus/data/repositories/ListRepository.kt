package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.dataClasses.ListEntry
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val firebaseListDao: FirebaseListDao
) {
    suspend fun storeListEntry(entry: ListEntry) = firebaseListDao.storeListEntry(entry)

    suspend fun deleteListEntry(entry: ListEntry) = firebaseListDao.deleteListEntry(entry)

    val allGames = firebaseListDao.getAll()

    val playing = firebaseListDao.getPlaying()

    val completed = firebaseListDao.getCompleted()

    val planned = firebaseListDao.getPlanned()

    val dropped = firebaseListDao.getDropped()

    val favorites = firebaseListDao.getFavorites()

    val doneFetching = firebaseListDao.doneFetching

    suspend fun getTop10Favorites() = firebaseListDao.getTop10Favorites()

    suspend fun getAllGamesAsState() = firebaseListDao.getAllGamesAsState()


}