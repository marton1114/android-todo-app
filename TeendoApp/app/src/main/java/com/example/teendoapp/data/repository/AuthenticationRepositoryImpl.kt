package com.example.teendoapp.data.repository

import com.example.teendoapp.data.model.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthenticationRepositoryImpl @Inject constructor (
    private val firebaseAuth: FirebaseAuth
): AuthenticationRepository {
    // User
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser
    override fun getCurrentUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    // Register
    override fun signUpUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>> = callbackFlow {
        firebaseAuth
            .createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    // Login
    override fun signInUserWithEmailAndPassword(
        email: String,
        password: String
    ): Flow<Response<Boolean>> = callbackFlow {
        firebaseAuth
            .signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    // Password reset email
    override fun sendPasswordResetEmail(
        email: String
    ): Flow<Response<Boolean>> = callbackFlow {
        firebaseAuth
            .sendPasswordResetEmail(email)
            .addOnSuccessListener {
                trySend(Response.Success(true))
            }
            .addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    // User sign in state
    override fun isUserSignedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }

    // Send email verification
    override fun sendEmailVerification(): Flow<Response<Boolean>> = callbackFlow {
        firebaseAuth
            .currentUser
            ?.sendEmailVerification()
            ?.addOnSuccessListener {
                trySend(Response.Success(true))
            }
            ?.addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    override fun reloadUser(): Flow<Response<Boolean>> = callbackFlow {
        firebaseAuth.currentUser
            ?.reload()
            ?.addOnSuccessListener {
                trySend(Response.Success(true))
            }
            ?.addOnFailureListener { e ->
                trySend(Response.Failure(e))
            }
        awaitClose()
    }

    override fun logoutUser() = firebaseAuth.signOut()

}