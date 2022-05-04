package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseListDao
import com.example.nexus.data.dataClasses.ListEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val firebaseListDao: FirebaseListDao
) {
    suspend fun storeListEntry(entry: ListEntry) = firebaseListDao.storeListEntry(entry)

    val allGames = firebaseListDao.getAll()

    val playing = firebaseListDao.getPlaying()

    val completed = firebaseListDao.getCompleted()

    val planned = firebaseListDao.getPlanned()

    val dropped = firebaseListDao.getDropped()

    val favorites = firebaseListDao.getFavorites()

}