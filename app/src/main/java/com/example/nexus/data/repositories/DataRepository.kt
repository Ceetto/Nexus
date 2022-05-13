package com.example.nexus.data.repositories

import android.net.Uri
import com.example.nexus.data.dataClasses.User
import com.example.nexus.data.db.FirebaseUserDao
import com.example.nexus.data.db.StorageDao
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