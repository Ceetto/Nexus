package com.example.nexus.data.db

import android.net.Uri
import com.example.nexus.data.repositories.DataRepository
import com.example.nexus.data.repositories.LoginRepository
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class StorageDao @Inject constructor(
storage: FirebaseStorage,
private val dataRepo: DataRepository
) {
    private val storageRef = storage.reference

    fun addPicture(picture: Uri, profilePic: Boolean){
        val fileName = UUID.randomUUID().toString() + ".jpg"
        val refStorage = storageRef.child("images/$fileName")

        refStorage.putFile(picture)
            .addOnSuccessListener { taskSnapshot ->
                taskSnapshot.storage.downloadUrl.addOnSuccessListener {
                    val imageUrl = it.toString()
                    if (profilePic) {
                        dataRepo.setProfilePicture(imageUrl)
                    } else {
                        dataRepo.setProfileBackground(imageUrl)
                    }
                }
            }
            .addOnFailureListener { e ->
                print(e.message)
            } }

}
