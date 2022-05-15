package com.example.nexus.data.db.list

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.ListEntry
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class FirebaseFriendListDao @Inject constructor(
    private val database: FirebaseDatabase,
){
    private val friendId = mutableStateOf("")
    private val friendRef = mutableStateOf(database.getReference("user/${friendId.value}/list"))


    fun setFriendId(id: String) {
        friendId.value = id
        updateFriend()
    }

    fun updateFriend(){
        friendRef.value = database.getReference("user/${friendId.value}/list")
        friendRef.value.addValueEventListener(eventListener)
    }

    private val TAG = "ListRepository"

    private var allGames = MutableStateFlow(emptyList<ListEntry>())

    private val eventListener = object: ValueEventListener {
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
                        child.child("favorited").value as Boolean,
                        child.child("releaseDate").value as Long,
                        child.child("notificationGiven").value as Boolean
                    )
                )
            }
            newList.sortBy { entry: ListEntry -> entry.title }
            allGames.update { newList }
        }
        override fun onCancelled(error: DatabaseError) {
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    fun getAll(): Flow<List<ListEntry>> {
        return allGames
    }
}