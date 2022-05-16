package com.example.nexus.data.repositories

import com.example.nexus.data.db.FirebaseUserDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val fireBaseUserDao: FirebaseUserDao
) {

    fun setProfilePicture(url: String){
        fireBaseUserDao.changeProfilePic(url)
    }

    fun setProfileBackground(url: String){
        fireBaseUserDao.changeBackground(url)
    }
}