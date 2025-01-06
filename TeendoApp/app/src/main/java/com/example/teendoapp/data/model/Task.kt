package com.example.teendoapp.data.model

data class Task(
    val id: String = "",
    val userId: String = "",
    val titleId: String = "",
    val title: String = "",
    val description: String = "",
    val progression: Int = 1,
    val creationTime: Long = 0L,
    val startTime: Long = 0L,
    val endTime: Long = 0L,
    val finishTime: Long = 0L,
    val finished: Boolean = false,
)
