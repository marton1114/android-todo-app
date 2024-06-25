package com.example.teendoapp.data.repository

import com.example.teendoapp.data.dao.TaskDao
import com.example.teendoapp.data.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl(
    private val taskDao: TaskDao
): TaskRepository {
    override fun getTasksFromRoom(): Flow<List<Task>> = taskDao.getTasks()

    override suspend fun addTaskToRoom(task: Task) =
        taskDao.addTask(task)

    override suspend fun deleteTaskFromRoom(task: Task) =
        taskDao.deleteTask(task)

    override suspend fun updateTaskFromRoom(task: Task) =
        taskDao.updateTask(task)

}