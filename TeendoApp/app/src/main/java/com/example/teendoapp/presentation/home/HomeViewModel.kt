package com.example.teendoapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teendoapp.data.model.Task
import com.example.teendoapp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
): ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        viewModelScope.launch {
            getTasks()
        }
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ChangeAddBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(
                    isAddBottomSheetVisible = ! uiState.isAddBottomSheetVisible
                )
            }
            is HomeUiEvent.ChangeImportanceValueEvent -> {
                uiState = uiState.copy(
                    importanceValue = event.newValue
                )
            }
            is HomeUiEvent.UpdateTitleValueEvent -> {
                uiState = uiState.copy(
                    titleValue = event.newValue
                )
            }
            is HomeUiEvent.UpdateDescriptionValueEvent -> {
                uiState = uiState.copy(
                    descriptionValue = event.newValue
                )
            }
            is HomeUiEvent.ChangeStartDatePickerDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isStartDatePickerDialogVisible = ! uiState.isStartDatePickerDialogVisible
                )
            }
            is HomeUiEvent.ChangeEndDatePickerDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isEndDatePickerDialogVisible = ! uiState.isEndDatePickerDialogVisible
                )
            }
            is HomeUiEvent.ChangeStartTimePickerDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isStartTimePickerDialogVisible = ! uiState.isStartTimePickerDialogVisible
                )
            }
            is HomeUiEvent.ChangeEndTimePickerDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isEndTimePickerDialogVisible = ! uiState.isEndTimePickerDialogVisible
                )
            }
            is HomeUiEvent.UpdateStartDateEvent -> {
                uiState = uiState.copy(
                    formattedStartDate = event.formattedDate,
                )
                uiState.startCalendar.set(
                    Calendar.YEAR, event.calendar.get(Calendar.YEAR),
                )
                uiState.startCalendar.set(
                    Calendar.MONTH, event.calendar.get(Calendar.MONTH),
                )
                uiState.startCalendar.set(
                    Calendar.DAY_OF_MONTH, event.calendar.get(Calendar.DAY_OF_MONTH),
                )
            }
            is HomeUiEvent.UpdateEndDateEvent -> {
                uiState = uiState.copy(
                    formattedEndDate = event.formattedDate,
                )
                uiState.endCalendar.set(
                    Calendar.YEAR, event.calendar.get(Calendar.YEAR),
                )
                uiState.endCalendar.set(
                    Calendar.MONTH, event.calendar.get(Calendar.MONTH),
                )
                uiState.endCalendar.set(
                    Calendar.DAY_OF_MONTH, event.calendar.get(Calendar.DAY_OF_MONTH),
                )
            }
            is HomeUiEvent.UpdateStartTimeEvent -> {
                uiState = uiState.copy(
                    formattedStartTime = event.formattedTime,
                )
                uiState.startCalendar.set(
                    Calendar.HOUR_OF_DAY, event.calendar.get(Calendar.HOUR),
                )
                uiState.startCalendar.set(
                    Calendar.MINUTE, event.calendar.get(Calendar.MINUTE),
                )
                uiState.startCalendar.set(
                    Calendar.SECOND, event.calendar.get(Calendar.SECOND),
                )
            }
            is HomeUiEvent.UpdateEndTimeEvent -> {
                uiState = uiState.copy(
                    formattedEndTime = event.formattedTime,
                )
                uiState.endCalendar.set(
                    Calendar.HOUR_OF_DAY, event.calendar.get(Calendar.HOUR),
                )
                uiState.endCalendar.set(
                    Calendar.MINUTE, event.calendar.get(Calendar.MINUTE),
                )
                uiState.endCalendar.set(
                    Calendar.SECOND, event.calendar.get(Calendar.SECOND),
                )
            }
            is HomeUiEvent.AddTaskEvent -> {
                addTask()

                val tempHomeUiState = HomeUiState()
                uiState = uiState.copy(
                    titleValue = tempHomeUiState.titleValue,
                    descriptionValue = tempHomeUiState.descriptionValue,
                    startCalendar= tempHomeUiState.startCalendar,
                    endCalendar = tempHomeUiState.endCalendar,
                    formattedStartDate = tempHomeUiState.formattedStartDate,
                    formattedEndDate = tempHomeUiState.formattedEndDate,
                    formattedStartTime = tempHomeUiState.formattedStartTime,
                    formattedEndTime = tempHomeUiState.formattedEndTime,
                    importanceValue = tempHomeUiState.importanceValue,
                )
            }
            is HomeUiEvent.ChangeIsSortedByImportanceValueEvent -> {
                uiState = uiState.copy(
                    isSortedByImportance = event.value
                )
            }
            is HomeUiEvent.ChangePostponementDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isPostponementDialogVisible = ! uiState.isPostponementDialogVisible
                )
            }
            is HomeUiEvent.SetTaskDoneEvent -> {
                updateTask(event.task.copy(isDone = true))
            }
            is HomeUiEvent.SetEditedTaskEvent -> {
                uiState = uiState.copy(
                    taskToEdit = event.task
                )
            }
            is HomeUiEvent.PostponeEditedTaskEvent -> {
                val calendar = Calendar.getInstance()
                calendar.timeInMillis = uiState.taskToEdit.endTime
                calendar.add(event.dateValueType, event.dateValue)
                calendar.add(event.timeValueType, event.timeValue)

                updateTask(uiState.taskToEdit.copy(
                    endTime = calendar.timeInMillis
                ))
            }
        }
    }

    private fun addTask() = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.addTaskToRoom(
            Task(
                title = uiState.titleValue,
                description = uiState.descriptionValue,
                importance = uiState.importanceValue,
                startTime = uiState.startCalendar.timeInMillis,
                endTime = uiState.endCalendar.timeInMillis
            )

        )
    }

    private fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.updateTaskFromRoom(task)
    }

    private suspend fun getTasks() {
        taskRepository.getTasksFromRoom().collect {
            uiState = uiState.copy(tasks = it)
        }
    }
}