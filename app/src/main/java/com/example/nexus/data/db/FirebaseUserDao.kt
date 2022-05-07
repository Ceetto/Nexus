package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserDao @Inject constructor(
    private val database: FirebaseDatabase
){
    private val userRef = database.getReference("user")

    var doneFetching = mutableStateOf(false)


//    private val realtimeEntries = userRef.addValueEventListener(
//        object: ValueEventListener {
//
//            override fun onDataChange(snapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                var newUser = mutableStateOf<User>()
//
//                var email: String,
//                val username: String,
//                var friends: List<String>,
//                var friendRequests: List<String>,
//                var profilePicture: String,
//                var profileBackground :String,
//                var releaseNotification : Long
//
//                for(child in snapshot.children ){
//                    newUser = User(
//                        child.child()
//
//
//                    )
//                        ListEntry(
//                            child.child("gameId").value as Long,
//                            child.child("title").value as String,
//                            (child.child("score").value as Long).toInt(),
//                            (child.child("minutesPlayed").value as Long).toInt(),
//                            child.child("status").value as String,
//                            child.child("coverUrl").value as String?,
//                            child.child("favorited").value as Boolean,
//                            child.child("releaseDate").value as Long
//                        )
//                    )
//                }                newList.sortBy { entry: ListEntry -> entry.title }
//                playing.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.PLAYING.value } }
//                completed.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.COMPLETED.value } }
//                planned.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.PLANNED.value } }
//                dropped.update { newList.filter { entry: ListEntry -> entry.status == ListCategory.DROPPED.value } }
//                allGames.update{ playing.value.plus(completed.value).plus(planned.value).plus(dropped.value) }
//                favorites.update{newList.filter { entry: ListEntry -> entry.favorited}}
//                top10Favorites.value = newList.filter { entry: ListEntry -> entry.favorited }.sortedBy {it.score}.reversed()
//                allGamesState.value = playing.value.plus(completed.value).plus(planned.value).plus(dropped.value)
//                doneFetching.value = true
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w(TAG, "Failed to read value.", error.toException())
//            }
//        }
//    )
//
//    private var user = MutableStateFlow(User)

//            }
//        })

}