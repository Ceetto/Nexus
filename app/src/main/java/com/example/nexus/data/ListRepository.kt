package com.example.nexus.data

import com.example.nexus.data.db.ListDao
import com.example.nexus.data.db.ListEntity
import com.example.nexus.data.web.ListBackend
import com.example.nexus.data.web.ListEntry
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepository @Inject constructor(
    private val backend: ListBackend,
    private val dao: ListDao
) {
    fun getBackendGames() = backend.getGames()

    suspend fun storeListEntry(entry: ListEntry) = dao.storeListEntry(entry)

    suspend fun deleteQuoter(entity: ListEntity) = dao.deleteListEntry(entity)

    val allGames = dao.getAll()

    val playing = dao.getPlaying()

    val completed = dao.getCompleted()

    val planned = dao.getPlanned()

    val dropped = dao.getDropped()
}