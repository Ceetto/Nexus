package com.example.nexus.data.db

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.nexus.data.dataClasses.Notification
import com.example.nexus.data.dataClasses.NotificationType
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

class FirebaseNotificationDao @Inject constructor(
    private val database: FirebaseDatabase,
    private val auth: FirebaseAuth
){
    private val notificationRef = mutableStateOf(database.getReference("user/${getUserId(auth.currentUser)}/notifications"))

    private val countNewNotifications = mutableStateOf(0)

    private val notifications = MutableStateFlow(emptyList<Notification>())

    private val eventListener =
        object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
//                 This method is called once with the initial value and again
//                 whenever data at this location is updated.
                val newNotications = mutableListOf<Notification>()
                countNewNotifications.value = 0
                for(child in snapshot.children){
                    newNotications.add(
                        Notification(
                            child.child("userId").value as String,
                            child.child("gameId").value as Long,
                            child.child("releaseDate").value as Long,
                            child.child("notificationTime").value as Long,
                            child.child("read").value as Boolean,
                            child.child("pfp").value as String?,
                            child.child("gameCover").value as String?,
                            child.child("username").value as String,
                            child.child("gameName").value as String,
                            child.child("notificationType").value as String,
                        )
                    )
                    //if there's an unread notification, set allNotificationsRead to false
                    if (!(child.child("read").value as Boolean)) {
                        countNewNotifications.value += 1
                    }
                }
                notifications.update{newNotications}
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }

    private val realtimeNotifications = mutableStateOf(notificationRef.value.addValueEventListener(eventListener))

    fun updateUser(){
        notificationRef.value = database.getReference("user/${getUserId(auth.currentUser)}/notifications")
        realtimeNotifications.value = notificationRef.value.addValueEventListener(eventListener)
    }

    fun getNotifications(): Flow<List<Notification>> {
        return notifications
    }

    fun storeNotification(notif: Notification){
        if(notif.notificationType == NotificationType.FRIEND_REQUEST.value){
            notificationRef.value.child(notif.userId).setValue(notif)
        } else {
            notificationRef.value.child(notif.gameId.toString()).setValue(notif)
        }
    }

    fun removeNotification(notif: Notification){
        if(notif.notificationType == NotificationType.FRIEND_REQUEST.value){
            notificationRef.value.child(notif.userId).removeValue()
        } else {
            notificationRef.value.child(notif.gameId.toString()).removeValue()
        }
    }

    fun countNewNotifications(): Int {
        return countNewNotifications.value
    }

    private val TAG = "NotificationDao"
}