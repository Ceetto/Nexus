package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.Friend
import com.example.nexus.data.dataClasses.getUserId
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val friendsData = MutableStateFlow(ArrayList<Friend>())

    private var friendsReferences = ArrayList<DatabaseReference>()

    private val eventListener =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.

            val newFriends = mutableListOf<String>()
            friendsReferences = ArrayList()
            friendsData.update { ArrayList() }
            for(child in snapshot.children){
                newFriends.add(child.value as String)
                val ref = database.getReference(("user/${child.value as String}"))
                friendsReferences.add(ref)
                ref.addValueEventListener(friendEventListener)

            }
                friends.update{newFriends}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val friendEventListener =
        object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
                val newFriend = Friend("", "", "", "")
                println("---------------------------- KEY ---------------------")
                println(snapshot.key.toString())
                newFriend.userId = snapshot.key.toString()
                newFriend.username = snapshot.child("username").value as String
                newFriend.profilePicture = snapshot.child("profilePicture").value as String
                newFriend.profileBackground = snapshot.child("profileBackground").value as String
                friendsData.value.add(newFriend)
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

    fun getFriends(): StateFlow<List<String>> {
        return friends
    }

    fun getFriendData() : StateFlow<List<Friend>>{
        return friendsData
    }


    fun storeFriend(id: String){
        friendsRef.value.child(id).setValue(id)
    }


}