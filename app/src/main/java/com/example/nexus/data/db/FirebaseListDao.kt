package com.example.nexus.data.db

import android.util.Log
import com.example.nexus.data.dataClasses.ListEntry
import com.example.nexus.ui.routes.list.ListCategory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseListDao @Inject constructor(
    private val database: FirebaseDatabase
){
    private val listEntryRef = database.getReference("listEntry")

    private val realtimeEntries = listEntryRef.addValueEventListener(
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                val newList = mutableListOf<ListEntry>()
                for(child in snapshot.children ){
                    newList.add(
                        ListEntry(
                            child.child("gameId").value as Long,
                            child.child("title").value as String,
                            (child.child("score").value as Long).toInt(),
                            (child.child("minutesPlayed").value as Long).toInt(),
                            child.child("status").value as String,
                            child.child("coverUrl").value as String?,
                            child.child("favorited").value as Boolean
                        )
                    )
                }
                playing.update {   newList.filter { entry: ListEntry -> entry.status == ListCategory.PLAYING.value } }
                completed.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.COMPLETED.value } }
                planned.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.PLANNED.value } }
                dropped.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.DROPPED.value } }
                allGames.update{ playing.value.plus(completed.value).plus(planned.value).plus(dropped.value)}

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }
    )

    private val TAG = "ListRepository"

    private var allGames = MutableStateFlow(emptyList<ListEntry>())
    private var playing = MutableStateFlow(emptyList<ListEntry>())
    private var completed = MutableStateFlow(emptyList<ListEntry>())
    private var planned = MutableStateFlow(emptyList<ListEntry>())
    private var dropped = MutableStateFlow(emptyList<ListEntry>())

    fun getAll(): Flow<List<ListEntry>> {
        return allGames
    }

    fun getPlaying(): Flow<List<ListEntry>> {
        return playing
    }

    fun getCompleted(): Flow<List<ListEntry>> {
        return completed
    }

    fun getPlanned(): Flow<List<ListEntry>> {
        return planned
    }

    fun getDropped(): Flow<List<ListEntry>> {
        return dropped
    }

    suspend fun storeListEntry(entry: ListEntry){
        listEntryRef.child(entry.gameId.toString()).setValue(entry)
    }
}