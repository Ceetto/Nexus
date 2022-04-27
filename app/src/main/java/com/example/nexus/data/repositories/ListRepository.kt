package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirestoreDatabase
import com.example.nexus.data.db.FirestoreListDao
import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.web.ListBackend
import com.example.nexus.data.web.ListEntry
import com.google.firebase.firestore.QuerySnapshot
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val backend: ListBackend,
    private val dao: ListDao,
    private val firestoreDao: FirestoreListDao
) {

    fun getBackendGames() = backend.getGames()

    suspend fun storeListEntry(entry: ListEntry) = dao.storeListEntry(entry)

    suspend fun deleteListEntry(entity: ListEntity) = dao.deleteListEntry(entity)

    fun getCategory(category: String) = dao.getCategory(category)

    suspend fun wipeDatabase() = dao.wipeDatabase()

    val allGames = dao.getAll()

    val playing = dao.getPlaying()

    val completed = dao.getCompleted()

    val planned = dao.getPlanned()

    val dropped = dao.getDropped()

    val firestoreTest = firestoreDao.getAll()
}