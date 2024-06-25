package com.example.teendoapp.data.repository

import com.example.teendoapp.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksFromRoom(): Flow<List<Task>>

    suspend fun addTaskToRoom(task: Task)

    suspend fun deleteTaskFromRoom(task: Task)

    suspend fun updateTaskFromRoom(task: Task)
}