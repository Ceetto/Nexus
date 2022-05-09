package com.example.nexus.data.db.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.FriendRequest
import com.example.nexus.data.dataClasses.ReleaseNotification
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

class FirebaseFriendRequestDao @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) {
    private val friendRequestRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}/friendRequests"))

    private val notifications = MutableStateFlow(emptyList<FriendRequest>())

    private val eventListener =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
                val newNotifications = mutableListOf<FriendRequest>()
                for(child in snapshot.children){
                    newNotifications.add(
                        FriendRequest(
                            child.child("userId").value as String,
                            child.child("requestTime").value as Long,
                        )
                    )
                }
                notifications.update{newNotifications}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val TAG = "UserRepository"

    private val realtimeNotications = mutableStateOf(friendRequestRef.value.addValueEventListener(eventListener))

    fun updateUser(){
        friendRequestRef.value = database.getReference("user/${getUserId(auth.currentUser)}/friendRequests")
        realtimeNotications.value = friendRequestRef.value.addValueEventListener(eventListener)
    }

    fun getFriendRequests(): Flow<List<FriendRequest>> {
        return notifications
    }

    //TODO
    //@Whoever dat dit gaat uitbreiden/gebruiken, note dat je hier waarschijnlijk eerder een
    //notication wilt sturen naar een andere user en niet naar uzelf, dus er gaan hier nog changes
    //nodig zijn
    fun storeFriendRequest(req: FriendRequest){
        friendRequestRef.value.child(req.userId).setValue(req)
    }
}