package com.example.teendoapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teendoapp.presentation.home.components.AddTaskBottomSheet
import com.example.teendoapp.presentation.home.components.CustomDatePickerDialog
import com.example.teendoapp.presentation.home.components.CustomTimePickerDialog
import com.example.teendoapp.presentation.home.components.PostponementDialog
import com.example.teendoapp.presentation.home.components.TaskElement
import com.example.teendoapp.ui.theme.BeetRootGrey97
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Teendők (${uiState.tasks.size} darab)", style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
            )
        },
        floatingActionButton = {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ExtendedFloatingActionButton(
                    text = { Text(text = "Hozzáadás") },
                    icon = { Icon(imageVector = Icons.Default.Add, contentDescription = null) },
                    onClick = { viewModel.onEvent(HomeUiEvent.ChangeAddBottomSheetVisibilityEvent) }
                )
            }
        },
        modifier = Modifier.background(BeetRootGrey97)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                TextButton(onClick = {
                    viewModel.onEvent(HomeUiEvent.ChangeIsSortedByImportanceValueEvent(true))
                }) {
                    Text(text = "Rendezés fontosság szerint",
                        color = if (uiState.isSortedByImportance) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (uiState.isSortedByImportance) TextDecoration.Underline
                        else TextDecoration.None)
                }
                VerticalDivider(modifier = Modifier.height(24.dp))
                TextButton(onClick = {
                    viewModel.onEvent(HomeUiEvent.ChangeIsSortedByImportanceValueEvent(false))
                }) {
                    Text(text = "Rendezés határidő szerint",
                        color = if (! uiState.isSortedByImportance) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface,
                        textDecoration = if (! uiState.isSortedByImportance) TextDecoration.Underline
                        else TextDecoration.None)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(12.dp, 0.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                contentPadding = PaddingValues(bottom = 56.dp)
            ) {
                val sortedTasks =
                    if (uiState.isSortedByImportance) {
                        uiState.tasks.sortedBy { task -> - task.importance }
                    } else {
                        uiState.tasks.sortedBy { task -> task.endTime }
                    }
                items(sortedTasks) { task ->
                    TaskElement(
                        task = task,
                        formattedStartTime = uiState.dateTimeFormatter.format(Date(task.startTime)),
                        formattedEndTime = uiState.dateTimeFormatter.format(Date(task.endTime)),
                        onPostponementButtonClick = {
                            viewModel.onEvent(HomeUiEvent.ChangePostponementDialogVisibilityEvent)
                            viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                        },
                        onDoneButtonClick = {
                            viewModel.onEvent(HomeUiEvent.SetTaskDoneEvent(task))
                        }
                    )
                }
            }
        }
    }

    if (uiState.isPostponementDialogVisible) {
        PostponementDialog(
            onDismiss = { viewModel.onEvent(HomeUiEvent.ChangePostponementDialogVisibilityEvent) },
            onConfirm = { dateValue, dateValueType, timeValue, timeValueType ->
                viewModel.onEvent(HomeUiEvent.PostponeEditedTaskEvent(dateValue, dateValueType, timeValue, timeValueType))
            }
        )
    }

    val sheetState = rememberModalBottomSheetState(true)
    if (uiState.isAddBottomSheetVisible) {
        AddTaskBottomSheet(
            sheetState = sheetState,
            onDismissRequest = { viewModel.onEvent(HomeUiEvent.ChangeAddBottomSheetVisibilityEvent) },
            taskTitleValue = uiState.titleValue,
            onTaskTitleValueChange = { viewModel.onEvent(HomeUiEvent.UpdateTitleValueEvent(it)) },
            taskDescriptionValue = uiState.descriptionValue,
            onTaskDescriptionValueChange = { viewModel.onEvent(HomeUiEvent.UpdateDescriptionValueEvent(it)) },
            startDateValue = uiState.formattedStartDate,
            endDateValue = uiState.formattedEndDate,
            startTimeValue = uiState.formattedStartTime,
            endTimeValue = uiState.formattedEndTime,
            importanceValue = uiState.importanceValue,
            onImportanceValueChange = { viewModel.onEvent(HomeUiEvent.ChangeImportanceValueEvent(it)) },
            onStartDateClick = { viewModel.onEvent(HomeUiEvent.ChangeStartDatePickerDialogVisibilityEvent) },
            onEndDateClick = { viewModel.onEvent(HomeUiEvent.ChangeEndDatePickerDialogVisibilityEvent) },
            onStartTimeClick = { viewModel.onEvent(HomeUiEvent.ChangeStartTimePickerDialogVisibilityEvent) },
            onEndTimeClick = { viewModel.onEvent(HomeUiEvent.ChangeEndTimePickerDialogVisibilityEvent) },
        ) {
            viewModel.onEvent(HomeUiEvent.AddTaskEvent)
        }
    }

    if (uiState.isStartDatePickerDialogVisible || uiState.isEndDatePickerDialogVisible) {
        CustomDatePickerDialog(
            onDateSelected = { formattedDate, calendar ->
                if (uiState.isStartDatePickerDialogVisible) {
                    viewModel.onEvent(HomeUiEvent.UpdateStartDateEvent(formattedDate, calendar))
                } else {
                    viewModel.onEvent(HomeUiEvent.UpdateEndDateEvent(formattedDate, calendar))
                }
            },
            dateFormatter = uiState.dateFormatter,
            onDismiss = {
                if (uiState.isStartDatePickerDialogVisible) {
                    viewModel.onEvent(HomeUiEvent.ChangeStartDatePickerDialogVisibilityEvent)
                } else {
                    viewModel.onEvent(HomeUiEvent.ChangeEndDatePickerDialogVisibilityEvent)
                }
            },
        )
    }
    if (uiState.isStartTimePickerDialogVisible || uiState.isEndTimePickerDialogVisible) {
        CustomTimePickerDialog(
            onDismiss = {
                if (uiState.isStartTimePickerDialogVisible) {
                    viewModel.onEvent(HomeUiEvent.ChangeStartTimePickerDialogVisibilityEvent)
                } else {
                    viewModel.onEvent(HomeUiEvent.ChangeEndTimePickerDialogVisibilityEvent)
                }
            },
            timeFormatter = uiState.timeFormatter,
            onTimeSelected = { formattedTime, calendar ->
                if (uiState.isStartTimePickerDialogVisible) {
                    viewModel.onEvent(HomeUiEvent.UpdateStartTimeEvent(formattedTime, calendar))
                } else {
                    viewModel.onEvent(HomeUiEvent.UpdateEndTimeEvent(formattedTime, calendar))
                }
            }
        )
    }
}