package com.example.teendoapp.data.repository

import com.example.teendoapp.data.model.Response
import com.example.teendoapp.data.model.Task
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasksByUserId(userId: String): Flow<Response<List<Task>>>

    suspend fun addTask(task: Task): Flow<Response<Boolean>>

    suspend fun deleteTaskById(taskId: String): Flow<Response<Boolean>>

    suspend fun updateTask(task: Task): Flow<Response<Boolean>>
}