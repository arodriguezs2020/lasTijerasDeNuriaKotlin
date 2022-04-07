package es.alvarorodriguez.lastijerasdenuria.repositories.auth

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    suspend fun signIn(email: String, password: String): FirebaseUser?
    suspend fun signUp(name: String, email: String, password: String, phone: String): FirebaseUser?
    suspend fun updateProfile(imageBitmap: Bitmap, username: String)
}