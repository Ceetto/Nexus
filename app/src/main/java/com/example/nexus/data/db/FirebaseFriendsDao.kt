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

    private val allMatchesRef  = mutableStateOf(database.getReference("user"))

    private val allMatches = MutableStateFlow(ArrayList<Friend>())

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


    private val allUserEventListener =
        object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                allMatches.value = ArrayList()

                for (child in snapshot.children){
                    val newUser = Friend("", "", "", "")

//                    println("search term = " + searchTerm.value)
//                    println("child key = " + child.key.toString())

                    if (Regex(".*${searchTerm.value.lowercase()}.*").matches(child.child("username").value.toString().lowercase())  && searchTerm.value != "") {
                        newUser.userId = child.key.toString()
                        newUser.username = child.child("username").value as String
                        newUser.profilePicture = child.child("profilePicture").value as String
                        newUser.profileBackground = child.child("profileBackground").value as String
//                        println("picture = " + newUser.profilePicture)
                        allMatches.value.add(newUser)
                    }
                }
                doneFetching.value = true
            }
            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val realtimeFriends = mutableStateOf(friendsRef.value.addValueEventListener(eventListener))
    var doneFetching = mutableStateOf(false)
    var searchTerm = mutableStateOf("")

    fun updateUser(){
        println("FRIENDS DAO UPDATE USER")
        friendsRef.value = database.getReference("user/${getUserId(auth.currentUser)}/friends")
        realtimeFriends.value = friendsRef.value.addValueEventListener(eventListener)
        allMatchesRef.value.addValueEventListener(allUserEventListener)
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
        updateUser()
    }
    
    fun storeYourself(id:String){
        val otherRef = database.getReference("user/$id/friends")
        otherRef.child(getUserId(auth.currentUser)).setValue(getUserId(auth.currentUser))
    }

    fun removeFriend(f: Friend) {
        friendsRef.value.child(f.userId).removeValue()
    }

    fun eventTrigger(){
        allMatchesRef.value.addValueEventListener(allUserEventListener)
    }

    fun getSearchResult() : StateFlow<List<Friend>> {
        return allMatches
    }

    fun emptyList() {
        allMatches.value = ArrayList()
    }

}