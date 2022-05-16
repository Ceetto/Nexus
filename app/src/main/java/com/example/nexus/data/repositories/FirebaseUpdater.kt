package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseFriendsDao
import com.example.nexus.data.db.FirebaseNotificationDao
import com.example.nexus.data.db.FirebaseUserDao
import com.example.nexus.data.db.list.FirebaseListDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUpdater @Inject constructor(
    private val userDao : FirebaseUserDao,
    private val listDao : FirebaseListDao,
    private val friendsDao : FirebaseFriendsDao,
    private val notifDao : FirebaseNotificationDao
){

    fun updateFirebaseData(){
        println("FIREBASE UPDATER CALLED")
        userDao.updateUser()
        listDao.updateUser()
        friendsDao.updateUser()
        notifDao.updateUser()
    }

}