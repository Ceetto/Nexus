package com.example.nexus.data.repositories

import android.net.Uri
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.db.FirebaseUserDao
import com.example.nexus.data.db.StorageDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileRepository @Inject constructor(
    private val fireBaseUserDao: FirebaseUserDao,
    private val storageDao : StorageDao
) {
    fun getUser(): User {
        return fireBaseUserDao.getUser()
    }

    fun updateUsername(username: String) {
        fireBaseUserDao.changeUsername(username)
    }

    fun updateProfilePicture(picture: Uri) {
        storageDao.addPicture(picture, true)
    }

    fun updateBackground(background: Uri) {
        storageDao.addPicture(background, false)
    }


    fun storeNewUser(user: User) {
        fireBaseUserDao.storeNewUser(user)
    }

    fun getFriend(): User {
        return fireBaseUserDao.getFriend()
    }

    fun updateUser() = fireBaseUserDao.updateUser()

    fun getUsername() = fireBaseUserDao.getUser().username

    fun getProfilePicture(): String {
        return fireBaseUserDao.getProfilePicture()
    }

    fun getBackground(): String {
        return fireBaseUserDao.getBackground()
    }

    fun setFriendId(id: String) {
        fireBaseUserDao.setFriendId(id)
    }
}