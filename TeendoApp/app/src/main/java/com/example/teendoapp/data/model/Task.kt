package com.example.teendoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val title: String = "",
    val description: String = "",
    val importance: Int = 5,
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val isDone: Boolean = false,
)
