package com.example.nexus.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.nexus.data.web.ListEntry
import kotlinx.coroutines.flow.Flow

@Dao
interface ListDao {

    @Query("select * from list")
    fun getAll(): Flow<List<ListEntity>>

    @Query("select * from list where status == 'Playing'")
    fun getPlaying(): Flow<List<ListEntity>>

    @Query("select * from list where status == 'Completed'")
    fun getCompleted(): Flow<List<ListEntity>>

    @Query("select * from list where status == 'Planned'")
    fun getPlanned(): Flow<List<ListEntity>>

    @Query("select * from list where status == 'Dropped'")
    fun getDropped(): Flow<List<ListEntity>>

    @Query("select * from list where status == :category")
    fun getCategory(category: String): Flow<List<ListEntity>>

    @Query("delete from list")
    suspend fun wipeDatabase()

    @Insert(entity = ListEntity::class)
    suspend fun storeListEntry(entry: ListEntry)

    @Delete
    suspend fun deleteListEntry(entity: ListEntity)
}