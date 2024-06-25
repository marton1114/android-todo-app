package com.example.teendoapp.presentation.home

import com.example.teendoapp.data.model.Task
import java.util.Calendar

interface HomeUiEvent {
    data object ChangeAddBottomSheetVisibilityEvent: HomeUiEvent
    data object ChangeStartDatePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangeEndDatePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangeStartTimePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangeEndTimePickerDialogVisibilityEvent: HomeUiEvent
    data object ChangePostponementDialogVisibilityEvent: HomeUiEvent
    data object AddTaskEvent: HomeUiEvent
    data class PostponeEditedTaskEvent(val dateValue: Int, val dateValueType: Int, val timeValue: Int, val timeValueType: Int): HomeUiEvent
    data class SetEditedTaskEvent(val task: Task): HomeUiEvent
    data class SetTaskDoneEvent(val task: Task): HomeUiEvent
    data class ChangeImportanceValueEvent(val newValue: Int): HomeUiEvent
    data class UpdateTitleValueEvent(val newValue: String): HomeUiEvent
    data class UpdateDescriptionValueEvent(val newValue: String): HomeUiEvent
    data class UpdateStartDateEvent(val formattedDate: String, val calendar: Calendar): HomeUiEvent
    data class UpdateEndDateEvent(val formattedDate: String, val calendar: Calendar): HomeUiEvent
    data class UpdateStartTimeEvent(val formattedTime: String, val calendar: Calendar): HomeUiEvent
    data class UpdateEndTimeEvent(val formattedTime: String, val calendar: Calendar): HomeUiEvent
    data class ChangeIsSortedByImportanceValueEvent(val value: Boolean): HomeUiEvent
}