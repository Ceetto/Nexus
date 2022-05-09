package com.example.nexus.data.db.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateOf
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
import javax.inject.Singleton

@Singleton
class ReleaseNotificationDao @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
) {
    private val releaseNotificationRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}/releaseNotifications"))

    private val notifications = MutableStateFlow(emptyList<ReleaseNotification>())

    private val eventListener =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
                val newNotications = mutableListOf<ReleaseNotification>()
                for(child in snapshot.children){
                    newNotications.add(
                        ReleaseNotification(
                            child.child("gameId").value as Long,
                            child.child("releaseDate").value as Long,
                            child.child("notificationTime").value as Long,
                        )
                    )
                }
                notifications.update{newNotications}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val TAG = "UserRepository"

    private val realtimeNotications = mutableStateOf(releaseNotificationRef.value.addValueEventListener(eventListener))

    fun updateUser(){
        releaseNotificationRef.value = database.getReference("user/${getUserId(auth.currentUser)}/releaseNotifications")
        realtimeNotications.value = releaseNotificationRef.value.addValueEventListener(eventListener)
    }

    fun getReleaseNotifications(): Flow<List<ReleaseNotification>>{
        return notifications
    }

    //TODO
    //@Whoever dat dit gaat uitbreiden/gebruiken, note dat je hier waarschijnlijk eerder een
    //notication wilt sturen naar een andere user en niet naar uzelf, dus er gaan hier nog changes
    //nodig zijn
    fun storeReleaseNotification(notif: ReleaseNotification){
        releaseNotificationRef.value.child(notif.gameId.toString()).setValue(notif)
    }
}