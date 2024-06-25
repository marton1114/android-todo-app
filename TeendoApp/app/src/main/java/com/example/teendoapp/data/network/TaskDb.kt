package com.example.teendoapp.data.network

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.teendoapp.data.dao.TaskDao
import com.example.teendoapp.data.model.Task

@Database(
    entities = [Task::class],
    version = 2,
    exportSchema = true
)
abstract class TaskDb: RoomDatabase() {
    abstract val taskDao: TaskDao
}