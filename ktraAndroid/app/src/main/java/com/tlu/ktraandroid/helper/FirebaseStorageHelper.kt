package com.tlu.ktraandroid.helper

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class FirebaseStorageHelper {
    fun uploadImageToFirebaseStorage(uri: Uri, onSuccess: (Uri) -> Unit, onFailure: (Exception) -> Unit) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

        val uploadTask = imageRef.putFile(uri)
        uploadTask.addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                onSuccess(downloadUri)
            }
        }.addOnFailureListener { exception ->
            onFailure(exception)
        }
    }
}