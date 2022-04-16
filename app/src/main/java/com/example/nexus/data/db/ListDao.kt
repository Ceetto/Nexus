package com.example.nexus.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nexus.data.web.ListEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Query("")
    fun getAll(): Flow<List<ListEntity>>

    @Query("")
    fun getPlaying(): Flow<List<ListEntity>>

    @Query("")
    fun getCompleted(): Flow<List<ListEntity>>

    @Query("")
    fun getPlanned(): Flow<List<ListEntity>>

    @Query("")
    fun getDropped(): Flow<List<ListEntity>>

    @Insert(entity = ListEntity::class)
    suspend fun storeListEntry(entry: ListEntry)

    @Delete
    suspend fun deleteListEntry(entity: ListEntity)
}