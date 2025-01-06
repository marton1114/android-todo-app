package com.example.teendoapp.presentation.home

import com.example.teendoapp.data.model.Task
import java.util.Calendar

interface HomeUiEvent {
    data object ChangeAddBottomSheetVisibilityEvent: HomeUiEvent
    data object ChangeStartDatePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangeEndDatePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangePostponementDialogVisibilityEvent: HomeUiEvent
    data object ChangeAlertDialogVisibilityEvent: HomeUiEvent
    data object ChangeFilterDialogVisibilityEvent: HomeUiEvent

    data object ClearFiltersEvent: HomeUiEvent
    data object ClearSearchBarValueEvent: HomeUiEvent
    data object AddTaskEvent: HomeUiEvent
    data object ChangeTaskDoneStateEvent: HomeUiEvent
    data object FilterTasksEvent: HomeUiEvent
    data object DeleteEditedTaskEvent: HomeUiEvent
    data object IncrementProgressionValueEvent: HomeUiEvent
    data object DecrementProgressionValueEvent: HomeUiEvent

    data class SetSelectedTabIndexEvent(val index: Int): HomeUiEvent
    data class PostponeEditedTaskEvent(val newTitleId: String, val newTitle: String,
                                       val newDescription: String, val newStartMillis: Long,
                                       val newEndMillis: Long, val newProgression: Int): HomeUiEvent
    data class SetEditedTaskEvent(val task: Task): HomeUiEvent
    data class ChangeImportanceValueEvent(val newValue: Int): HomeUiEvent

    data class UpdateTitleIdValueEvent(val newValue: String): HomeUiEvent
    data class UpdateTitleValueEvent(val newValue: String): HomeUiEvent
    data class UpdateSearchBarValueEvent(val newValue: String): HomeUiEvent
    data class UpdateDescriptionValueEvent(val newValue: String): HomeUiEvent
    data class UpdateStartDateEvent(val formattedDate: String, val calendar: Calendar): HomeUiEvent
    data class UpdateEndDateEvent(val formattedDate: String, val calendar: Calendar): HomeUiEvent
    data class UpdateFilterValuesEvent(val newShowDoneTasks: Boolean, val newShowNotDoneTasks: Boolean,
                                       val newFilterByDateInterval: Boolean, val newFilterStartMillis: Long,
                                       val newFilterEndMillis: Long): HomeUiEvent
}