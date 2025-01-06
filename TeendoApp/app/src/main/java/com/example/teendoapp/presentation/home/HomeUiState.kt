package com.example.teendoapp.presentation.home

import com.example.teendoapp.data.model.Task
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

data class HomeUiState(
    val selectedTabIndex: Int = 0,

    val dateFormatter: SimpleDateFormat =
        SimpleDateFormat("yyyy. MM. dd.", Locale("hu", "HU")),

    val everyTask: List<Task> = emptyList(),
    val filteredTasks: List<Task> = emptyList(),
    val actualTasks: List<Task> = emptyList(),
    val taskToEdit: Task = Task(),


    val searchBarValue: String = "",

    val defaultShowDoneTasks: Boolean = true,
    val defaultShowNotDoneTasks: Boolean = true,
    val defaultFilterByDateInterval: Boolean = false,
    val defaultFilterStartMillis: Long = 0,
    val defaultFilterEndMillis: Long = 3155752800074L,

    val showDoneTasks: Boolean = defaultShowDoneTasks,
    val showNotDoneTasks: Boolean = defaultShowNotDoneTasks,
    val filterByDateInterval: Boolean = defaultFilterByDateInterval,
    val filterStartMillis: Long = defaultFilterStartMillis,
    val filterEndMillis: Long = defaultFilterEndMillis,


    val isAddBottomSheetVisible: Boolean = false,
    val isStartDatePickerDialogVisible: Boolean = false,
    val isEndDatePickerDialogVisible: Boolean = false,
    val isSortedByImportance: Boolean = true,
    val isPostponementDialogVisible: Boolean = false,
    val isAlertDialogVisible: Boolean = false,
    val isFilterDialogVisible: Boolean = false,


    val titleIdValue: String = "",
    val titleValue: String = "",
    val descriptionValue: String = "",

    val startCalendar: Calendar = Calendar.getInstance(TimeZone.getDefault()),
    val endCalendar: Calendar = Calendar.getInstance(TimeZone.getDefault()),

    val formattedStartDate: String = dateFormatter.format(startCalendar.time),
    val formattedEndDate: String = dateFormatter.format(endCalendar.time),

    val importanceValue: Int = 1,

    val currentTimeMillis: Long = System.currentTimeMillis(),
)