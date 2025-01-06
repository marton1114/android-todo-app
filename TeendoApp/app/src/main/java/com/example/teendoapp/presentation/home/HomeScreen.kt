package com.example.teendoapp.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teendoapp.presentation.home.components.AddTaskBottomSheet
import com.example.teendoapp.presentation.home.components.CustomDatePickerDialog
import com.example.teendoapp.presentation.home.components.FilterDialog
import com.example.teendoapp.presentation.home.components.PostponementDialog
import com.example.teendoapp.presentation.home.components.TabItem
import com.example.teendoapp.presentation.home.components.TaskElement
import com.example.teendoapp.ui.theme.BlueGrey97
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState
    val tabItems = listOf(
        TabItem("Aktuális"),
        TabItem("Összes")
    )
    val pagerState = rememberPagerState {
        tabItems.size
    }
    LaunchedEffect(uiState.selectedTabIndex) {
        pagerState.animateScrollToPage(uiState.selectedTabIndex)
    }
    LaunchedEffect(pagerState.currentPage) {
        viewModel.onEvent(HomeUiEvent.SetSelectedTabIndexEvent(pagerState.currentPage))
    }

    val isFiltered = (uiState.showDoneTasks == uiState.defaultShowDoneTasks &&
            uiState.showNotDoneTasks == uiState.defaultShowNotDoneTasks &&
            uiState.filterByDateInterval == uiState.defaultFilterByDateInterval &&
            uiState.filterStartMillis == uiState.defaultFilterStartMillis &&
            uiState.filterEndMillis == uiState.defaultFilterEndMillis)

    Scaffold(
//        contentWindowInsets = WindowInsets.ime,
        topBar = {
            TopAppBar(
                navigationIcon = {},
                title = {
                    if (uiState.selectedTabIndex == 0) {
                        Text(text = "Teendők (${uiState.actualTasks.size} darab)", style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                    } else if (uiState.selectedTabIndex == 1) {
                        val interactionSource = remember { MutableInteractionSource() }
                        BasicTextField(
                            value = uiState.searchBarValue,
                            onValueChange = {
                                viewModel.onEvent(HomeUiEvent.UpdateSearchBarValueEvent(it))
                                viewModel.onEvent(HomeUiEvent.FilterTasksEvent)
                            },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                        ) { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = null) },
                                trailingIcon = {
                                    if (uiState.searchBarValue.isNotEmpty()) {
                                        IconButton(onClick = {
                                            viewModel.onEvent(HomeUiEvent.ClearSearchBarValueEvent)
                                        }) {
                                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                                        }
                                    }
                                },
                                value = uiState.searchBarValue,
                                visualTransformation = VisualTransformation.None,
                                innerTextField = innerTextField,
                                singleLine = true,
                                enabled = true,
                                interactionSource = interactionSource,
                                contentPadding = PaddingValues(0.dp),
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    if (uiState.selectedTabIndex == 1) {
                        FilledIconButton(onClick = {
                            viewModel.onEvent(HomeUiEvent.ChangeFilterDialogVisibilityEvent)
                        }) {
                            Icon(imageVector = Icons.Default.FilterAlt, contentDescription = null)
                        }
                        AnimatedVisibility(
                            visible = !isFiltered,
                            enter = slideInHorizontally { 100 },
                            exit = slideOutHorizontally { 100 }
                        ) {
                            FilledIconButton(onClick = {
                                viewModel.onEvent(HomeUiEvent.ClearFiltersEvent)
                            }) {
                                Icon(imageVector = Icons.Default.FilterAltOff, contentDescription = null)
                            }
                        }
                    }
                }
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
                    onClick = { viewModel.onEvent(HomeUiEvent.ChangeAddBottomSheetVisibilityEvent) },
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier
                        .border(2.dp, MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(15.dp))
                )
            }
        },
        modifier = Modifier.background(BlueGrey97)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TabRow(selectedTabIndex = uiState.selectedTabIndex) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = (index == uiState.selectedTabIndex),
                        onClick = {
                            viewModel.onEvent(HomeUiEvent.SetSelectedTabIndexEvent(index))
                        },
                        text = { Text(text = item.title) }
                    )
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1F),
                userScrollEnabled = false,
            ) { index ->
                if (index == 0) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 68.dp)
                    ) {
                        items(
                            items = uiState.actualTasks,
                            key = { it.id }
                        ) { task ->
                            TaskElement(
                                task = task,
                                currentTimeMillis = uiState.currentTimeMillis,
                                formattedCreationTime = uiState.dateFormatter.format(Date(task.creationTime)),
                                formattedStartTime = uiState.dateFormatter.format(Date(task.startTime)),
                                formattedEndTime = uiState.dateFormatter.format(Date(task.endTime)),
                                formattedFinishTime = uiState.dateFormatter.format(Date(task.finishTime)),
                                onIncrementProgression = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.IncrementProgressionValueEvent)
                                },
                                onDecrementProgression = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.DecrementProgressionValueEvent)
                                },
                                onPostponementButtonClick = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.ChangePostponementDialogVisibilityEvent)
                                },
                                onDoneButtonClick = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.ChangeAlertDialogVisibilityEvent)
                                }
                            )
                        }
                    }
                } else if (index == 1) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 68.dp)
                    ) {
                        items(
                            items = uiState.filteredTasks,
                            key = { it.id }
                        ) { task ->
                            TaskElement(
                                task = task,
                                currentTimeMillis = uiState.currentTimeMillis,
                                formattedCreationTime = uiState.dateFormatter.format(Date(task.creationTime)),
                                formattedStartTime = uiState.dateFormatter.format(Date(task.startTime)),
                                formattedEndTime = uiState.dateFormatter.format(Date(task.endTime)),
                                formattedFinishTime = uiState.dateFormatter.format(Date(task.finishTime)),
                                onIncrementProgression = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.IncrementProgressionValueEvent)
                                },
                                onDecrementProgression = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.DecrementProgressionValueEvent)
                                },
                                onPostponementButtonClick = {
                                    viewModel.onEvent(HomeUiEvent.ChangePostponementDialogVisibilityEvent)
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                },
                                onDoneButtonClick = {
                                    viewModel.onEvent(HomeUiEvent.SetEditedTaskEvent(task))
                                    viewModel.onEvent(HomeUiEvent.ChangeAlertDialogVisibilityEvent)
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    if (uiState.isFilterDialogVisible) {
        FilterDialog(
            showDoneTasks = uiState.showDoneTasks,
            showNotDoneTasks = uiState.showNotDoneTasks,
            filterByDateInterval = uiState.filterByDateInterval,
            filterStartMillis = uiState.filterStartMillis,
            filterEndMillis = uiState.filterEndMillis,
            dateFormatter = uiState.dateFormatter,
            onClearFilters = {
                viewModel.onEvent(HomeUiEvent.ClearFiltersEvent)

            },
            onDismiss = {
                viewModel.onEvent(HomeUiEvent.ChangeFilterDialogVisibilityEvent)
            },
            onConfirm = { newShowDoneTasks, newShowNotDoneTasks,
                newFilterByDateInterval, newFilterStartMillis, newFilterEndMillis ->

                viewModel.onEvent(HomeUiEvent.UpdateFilterValuesEvent(
                    newShowDoneTasks, newShowNotDoneTasks,
                    newFilterByDateInterval, newFilterStartMillis, newFilterEndMillis
                ))
            }
        )
    }

    if (uiState.isAlertDialogVisible) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onEvent(HomeUiEvent.ChangeAlertDialogVisibilityEvent)
            },
            dismissButton = {
                OutlinedButton(onClick = { viewModel.onEvent(HomeUiEvent.ChangeAlertDialogVisibilityEvent) }) {
                    Text(text = "Mégse")
                }
            },
            title = { Text(text = "Megerősítés", style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()) },
            text = { Text(text = "Biztos benne, hogy végre szeretné hajtani ezt a műveletet?") },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.onEvent(HomeUiEvent.ChangeTaskDoneStateEvent)
                        viewModel.onEvent(HomeUiEvent.ChangeAlertDialogVisibilityEvent)
                    }
                ) {
                    Text(text = "Megerősít")
                }
            },
        )
    }

    if (uiState.isPostponementDialogVisible) {
        PostponementDialog(
            titleId = uiState.taskToEdit.titleId,
            title = uiState.taskToEdit.title,
            description = uiState.taskToEdit.description,
            startMillis = uiState.taskToEdit.startTime,
            endMillis = uiState.taskToEdit.endTime,
            dateFormatter = uiState.dateFormatter,
            importance = uiState.taskToEdit.progression,
            onDelete = {
                viewModel.onEvent(HomeUiEvent.DeleteEditedTaskEvent)
            },
            onDismiss = { viewModel.onEvent(HomeUiEvent.ChangePostponementDialogVisibilityEvent) },
            onConfirm = { newTitleId, newTitle, newDescription, newStartMillis, newEndMillis, newImportance ->
                viewModel.onEvent(HomeUiEvent.PostponeEditedTaskEvent(
                    newTitleId, newTitle, newDescription, newStartMillis, newEndMillis, newImportance
                ))
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
            taskTitleIdValue = uiState.titleIdValue,
            onTaskTitleIdValueChange = { viewModel.onEvent(HomeUiEvent.UpdateTitleIdValueEvent(it)) },
            taskDescriptionValue = uiState.descriptionValue,
            onTaskDescriptionValueChange = { viewModel.onEvent(HomeUiEvent.UpdateDescriptionValueEvent(it)) },
            startDateValue = uiState.formattedStartDate,
            endDateValue = uiState.formattedEndDate,
            importanceValue = uiState.importanceValue,
            onImportanceValueChange = { viewModel.onEvent(HomeUiEvent.ChangeImportanceValueEvent(it)) },
            onStartDateClick = { viewModel.onEvent(HomeUiEvent.ChangeStartDatePickerDialogVisibilityEvent) },
            onEndDateClick = { viewModel.onEvent(HomeUiEvent.ChangeEndDatePickerDialogVisibilityEvent) },
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
}