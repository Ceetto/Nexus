package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.nexus.data.dataClasses.getUserId
import com.google.firebase.auth.FirebaseAuth
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
class FirebaseFriendsDao @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
){
    private val friendsRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}/friends"))

    private val friends = MutableStateFlow(emptyList<String>())

    private val eventListener =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
            val newFriends = mutableListOf<String>()
            for(child in snapshot.children){
                newFriends.add(child.value as String)
            }
                friends.update{newFriends}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val realtimeFriends = mutableStateOf(friendsRef.value.addValueEventListener(eventListener))

    fun updateUser(){
        friendsRef.value = database.getReference("user/${getUserId(auth.currentUser)}/friends")
        realtimeFriends.value = friendsRef.value.addValueEventListener(eventListener)
    }

    private val TAG = "UserRepository"

    fun getFriends(): Flow<List<String>>{
        return friends
    }

    fun storeFriend(id: String){
        friendsRef.value.setValue(id)
    }
}