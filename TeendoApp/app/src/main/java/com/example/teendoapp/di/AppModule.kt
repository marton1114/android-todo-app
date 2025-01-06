package com.example.teendoapp.di


import com.example.teendoapp.data.repository.AuthenticationRepository
import com.example.teendoapp.data.repository.AuthenticationRepositoryImpl
import com.example.teendoapp.data.repository.TaskRepository
import com.example.teendoapp.data.repository.TaskRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideTaskRepository(
        @Named("task-collection") taskCollectionReference: CollectionReference
    ): TaskRepository = TaskRepositoryImpl(
        taskCollectionReference
    )

    @Provides
    fun provideAuthenticationRepository(
        firebaseAuth: FirebaseAuth
    ): AuthenticationRepository = AuthenticationRepositoryImpl(
        firebaseAuth = firebaseAuth
    )
}
