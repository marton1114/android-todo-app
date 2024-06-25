package com.example.teendoapp.di

import android.content.Context
import androidx.room.Room
import com.example.teendoapp.data.dao.TaskDao
import com.example.teendoapp.data.network.TaskDb
import com.example.teendoapp.data.repository.TaskRepository
import com.example.teendoapp.data.repository.TaskRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideTaskDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        TaskDb::class.java,
        "task_table"
    ).build()

    @Provides
    fun provideTaskDao(taskDb: TaskDb) = taskDb.taskDao

    @Provides
    fun provideTaskRepository(
        taskDao: TaskDao
    ): TaskRepository = TaskRepositoryImpl(
        taskDao = taskDao
    )
}
