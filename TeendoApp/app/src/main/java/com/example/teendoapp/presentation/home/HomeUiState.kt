package com.example.teendoapp.presentation.home

import com.example.teendoapp.data.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class HomeUiState(
    val dateFormatter: SimpleDateFormat = SimpleDateFormat("yyyy. MM. dd.",
        Locale("hu", "HU")),
    val timeFormatter: SimpleDateFormat = SimpleDateFormat("HH:mm",
        Locale("hu", "HU")),
    val dateTimeFormatter: SimpleDateFormat = SimpleDateFormat("yyyy. MM. dd. HH:mm",
        Locale("hu", "HU")),

    val tasks: List<Task> = emptyList(),
    val taskToEdit: Task = Task(),

    val isAddBottomSheetVisible: Boolean = false,
    val isStartDatePickerDialogVisible: Boolean = false,
    val isEndDatePickerDialogVisible: Boolean = false,
    val isStartTimePickerDialogVisible: Boolean = false,
    val isEndTimePickerDialogVisible: Boolean = false,
    val isSortedByImportance: Boolean = true,
    val isPostponementDialogVisible: Boolean = false,

    val titleValue: String = "",
    val descriptionValue: String = "",

    val startCalendar: Calendar = Calendar.getInstance(TimeZone.getDefault()),
    val endCalendar: Calendar = Calendar.getInstance(TimeZone.getDefault()),

    val formattedStartDate: String = dateFormatter.format(startCalendar.time),
    val formattedEndDate: String = dateFormatter.format(endCalendar.time),

    val formattedStartTime: String = timeFormatter.format(startCalendar.time),
    val formattedEndTime: String = timeFormatter.format(endCalendar.time),

    val importanceValue: Int = 1,

    )