package com.example.nexus.data.repositories

import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.db.FirebaseUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val fireBaseUserDao: FirebaseUserDao
) {
    fun getUser(): User {
        return fireBaseUserDao.getUser()
    }

    fun updateUsername(username : String) {
        //update
    }

    fun updateEmail(username : String) {
        //update
    }

    fun addFriend(email : String) {
        //update
    }

    fun removeFriend(email: String){

    }

    fun removeFriendRequest(email : String){

    }
    fun addFriendRequest(email : String) {
        //update
    }

    fun updateProfilePicture(picture : String) {
        //update
    }

    fun updateBackground(background : String) {
        //update
    }

    fun updateNotifification(new : Long){
        //update
    }
}