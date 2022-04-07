package es.alvarorodriguez.lastijerasdenuria.repositories.auth

import android.graphics.Bitmap
import com.google.firebase.auth.FirebaseUser
import es.alvarorodriguez.lastijerasdenuria.data.remote.auth.AuthDataSource

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? = dataSource.signIn(email, password)
    override suspend fun signUp(name: String, email: String, password: String, phone: String): FirebaseUser? = dataSource.signUp(name, email, password, phone)
    override suspend fun updateProfile(imageBitmap: Bitmap, username: String) = dataSource.updateUserProfile(imageBitmap, username)
}