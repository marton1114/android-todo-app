package com.example.teendoapp.presentation.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.teendoapp.data.model.Response
import com.example.teendoapp.data.model.Task
import com.example.teendoapp.data.repository.AuthenticationRepository
import com.example.teendoapp.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    private val authenticationRepository: AuthenticationRepository,
): ViewModel() {
    var uiState by mutableStateOf(HomeUiState())
        private set

    init {
        getTasks()

        viewModelScope.launch {
            while (true) {
                delay(2000L)
                uiState = uiState.copy(
                    currentTimeMillis = System.currentTimeMillis()
                )
            }
        }.start()
    }

    fun onEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ChangeAddBottomSheetVisibilityEvent -> {
                uiState = uiState.copy(
                    isAddBottomSheetVisible = ! uiState.isAddBottomSheetVisible
                )

                uiState = uiState.copy(
                    titleIdValue = getNextTitleId().toString()
                )
            }
            is HomeUiEvent.ChangeImportanceValueEvent -> {
                uiState = uiState.copy(
                    importanceValue = event.newValue
                )
            }
            is HomeUiEvent.UpdateTitleIdValueEvent -> {
                uiState = uiState.copy(
                    titleIdValue = event.newValue
                )
            }
            is HomeUiEvent.UpdateTitleValueEvent -> {
                uiState = uiState.copy(
                    titleValue = event.newValue
                )
            }
            is HomeUiEvent.UpdateSearchBarValueEvent -> {
                uiState = uiState.copy(
                    searchBarValue = event.newValue
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
            is HomeUiEvent.ChangeAlertDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isAlertDialogVisible = ! uiState.isAlertDialogVisible
                )
            }
            is HomeUiEvent.ChangeEndDatePickerDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isEndDatePickerDialogVisible = ! uiState.isEndDatePickerDialogVisible
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
            is HomeUiEvent.AddTaskEvent -> {
                addTask()

                val tempHomeUiState = HomeUiState()
                uiState = uiState.copy(
                    titleIdValue = tempHomeUiState.titleIdValue,
                    titleValue = tempHomeUiState.titleValue,
                    descriptionValue = tempHomeUiState.descriptionValue,
                    startCalendar= tempHomeUiState.startCalendar,
                    endCalendar = tempHomeUiState.endCalendar,
                    formattedStartDate = tempHomeUiState.formattedStartDate,
                    formattedEndDate = tempHomeUiState.formattedEndDate,
                    importanceValue = tempHomeUiState.importanceValue,
                )
            }
            is HomeUiEvent.ChangePostponementDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isPostponementDialogVisible = ! uiState.isPostponementDialogVisible
                )
            }
            is HomeUiEvent.ChangeFilterDialogVisibilityEvent -> {
                uiState = uiState.copy(
                    isFilterDialogVisible = ! uiState.isFilterDialogVisible
                )
            }
            is HomeUiEvent.ChangeTaskDoneStateEvent -> {
                updateTask(
                    uiState.taskToEdit.copy(
                        finished = ! uiState.taskToEdit.finished,
                        finishTime = System.currentTimeMillis()
                    )
                )
            }
            is HomeUiEvent.SetEditedTaskEvent -> {
                uiState = uiState.copy(
                    taskToEdit = event.task
                )
            }
            is HomeUiEvent.PostponeEditedTaskEvent -> {
                updateTask(uiState.taskToEdit.copy(
                    titleId = event.newTitleId,
                    title = event.newTitle,
                    description = event.newDescription,
                    startTime = event.newStartMillis,
                    endTime = event.newEndMillis,
                    progression = event.newProgression
                ))
            }
            is HomeUiEvent.SetSelectedTabIndexEvent -> {
                uiState = uiState.copy(
                    selectedTabIndex = event.index
                )
            }
            is HomeUiEvent.DeleteEditedTaskEvent -> {
                deleteTask(uiState.taskToEdit.id)
            }
            is HomeUiEvent.FilterTasksEvent -> {
                filterTasks()
            }
            is HomeUiEvent.ClearSearchBarValueEvent -> {
                uiState = uiState.copy(
                    searchBarValue = ""
                )
                filterTasks()
            }
            is HomeUiEvent.ClearFiltersEvent -> {
                uiState = uiState.copy(
                    showDoneTasks = uiState.defaultShowDoneTasks,
                    showNotDoneTasks = uiState.defaultShowDoneTasks,
                    filterByDateInterval = uiState.defaultFilterByDateInterval,
                    filterStartMillis = uiState.defaultFilterStartMillis,
                    filterEndMillis = uiState.defaultFilterEndMillis,
                )
                filterTasks()
            }
            is HomeUiEvent.UpdateFilterValuesEvent -> {
                uiState = uiState.copy(
                    showDoneTasks = event.newShowDoneTasks,
                    showNotDoneTasks = event.newShowNotDoneTasks,
                    filterByDateInterval = event.newFilterByDateInterval,
                    filterStartMillis = event.newFilterStartMillis,
                    filterEndMillis = event.newFilterEndMillis
                )
                filterTasks()
            }
            is HomeUiEvent.DecrementProgressionValueEvent ->{
                if (uiState.taskToEdit.progression > 1)
                    updateTask(
                        uiState.taskToEdit.copy(
                            progression = uiState.taskToEdit.progression - 1
                        )
                    )
            }
            is HomeUiEvent.IncrementProgressionValueEvent ->{
                if (uiState.taskToEdit.progression < 10)
                    updateTask(
                        uiState.taskToEdit.copy(
                            progression = uiState.taskToEdit.progression + 1
                        )
                    )
            }
        }
    }

    private fun addTask() = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.addTask(
            Task(
                userId = authenticationRepository.getCurrentUserId(),
                titleId = uiState.titleIdValue,
                title = uiState.titleValue,
                description = uiState.descriptionValue,
                progression = uiState.importanceValue,
                creationTime = System.currentTimeMillis(),
                startTime = uiState.startCalendar.timeInMillis,
                endTime = uiState.endCalendar.timeInMillis
            )
        ).collect { response ->
            when (response) {
                is Response.Success -> {
                    filterTasks()
                }
                else -> {}
            }
        }
    }

    private fun updateTask(task: Task) = viewModelScope.launch(Dispatchers.IO) {
        taskRepository.updateTask(task).collect {

        }
    }

    private fun filterTasks() {
        uiState = uiState.copy(
            actualTasks = uiState.everyTask.filter {
                (!it.finished && (System.currentTimeMillis() >= it.startTime)) ||
                (it.finished && (System.currentTimeMillis() >= it.startTime) && (System.currentTimeMillis() <= it.endTime))
            }.sortedBy {
                it.endTime
            }
//            .sortedBy {
//                it.endTime > System.currentTimeMillis()
//            }
        )

        var filteredTasks = uiState.everyTask.sortedBy {
            if (it.titleId.isNotEmpty() && it.titleId.isDigitsOnly())
                it.titleId.toInt()
            else
                it.id.hashCode()
        }

        if (uiState.searchBarValue.isNotEmpty()) {
            filteredTasks = filteredTasks.filter {
                (
                    it.titleId +
                    it.title +
                    it.description +
                    it.title.lowercase() +
                    it.description.lowercase() +
                    it.title.trim() +
                    it.description.trim() +
                    uiState.dateFormatter.format(Date(it.startTime)) +
                    uiState.dateFormatter.format(Date(it.endTime))
                ).contains(uiState.searchBarValue)
            }
        }

        if (!uiState.showDoneTasks && !uiState.showNotDoneTasks) {
            filteredTasks = filteredTasks.filter { !it.finished && it.finished }
        } else if (uiState.showDoneTasks && !uiState.showNotDoneTasks) {
            filteredTasks = filteredTasks.filter { it.finished }
        } else if (!uiState.showDoneTasks && uiState.showNotDoneTasks) {
            filteredTasks = filteredTasks.filter { !it.finished }
        }

        if (uiState.filterByDateInterval) {
            filteredTasks = filteredTasks.filter { task ->
                task.startTime >= uiState.filterStartMillis &&
                task.endTime <= uiState.filterEndMillis
            }
        }

        uiState = uiState.copy(
            filteredTasks = filteredTasks
        )
    }

    private fun getTasks() = viewModelScope.launch {
        taskRepository
            .getTasksByUserId(authenticationRepository.getCurrentUserId())
            .collect { response ->

                when (response) {
                    is Response.Success -> {
                        uiState = uiState.copy(
                            everyTask = response.data,
                        )

                        filterTasks()
                    }
                    is Response.Failure -> {

                    }
                    else -> {}
                }
            }
    }

    private fun deleteTask(id: String) = viewModelScope.launch {
        taskRepository.deleteTaskById(id).collect {

        }
    }

    private fun getNextTitleId(): Int {
//// DEPRECATED: ALWAYS FINDS THE LOWEST VALUE
//        val takenTitleIds = uiState.everyTask
//            .filter { it.titleId.isNotEmpty() && it.titleId.isDigitsOnly() }
//            .map { it.titleId.toInt() }
//
//        var i = 1
//        while (takenTitleIds.contains(i)) {
//            i++
//        }
//        return i
        return uiState.everyTask.maxBy { it.titleId.toInt() }.titleId.toInt() + 1
    }
}