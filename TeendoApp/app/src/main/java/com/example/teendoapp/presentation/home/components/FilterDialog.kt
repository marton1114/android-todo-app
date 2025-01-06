package com.example.teendoapp.presentation.home.components

import android.app.Activity
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FilterAltOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    showDoneTasks: Boolean,
    showNotDoneTasks: Boolean,
    filterByDateInterval: Boolean,
    filterStartMillis: Long,
    filterEndMillis: Long,
    dateFormatter: SimpleDateFormat,
    onClearFilters: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm:
        (newShowDoneTasks: Boolean,
         newShowNotDoneTasks: Boolean,
         newFilterByDateInterval: Boolean,
         newFilterStartMillis: Long,
         newFilterEndMillis: Long) -> Unit
) {
    var tempShowDoneTasks by remember { mutableStateOf(showDoneTasks) }
    var tempShowNotDoneTasks by remember { mutableStateOf(showNotDoneTasks) }
    var tempFilterByDateInterval by remember { mutableStateOf(filterByDateInterval) }
    var tempFilterStartMillis by remember { mutableLongStateOf(filterStartMillis) }
    var tempFormattedFilterStartMillis by remember {
        mutableStateOf(dateFormatter.format(Date(filterStartMillis)))
    }
    var tempFilterEndMillis by remember { mutableLongStateOf(filterEndMillis) }
    var tempFormattedFilterEndMillis by remember {
        mutableStateOf(dateFormatter.format(Date(filterEndMillis)))
    }

    val radioOptions = listOf("Kész és nem kész","Csak kész","Csak nem kész")
    val initiallySelectedRadioOptionIndex =
        if (tempShowDoneTasks && tempShowNotDoneTasks) 0
        else if (tempShowDoneTasks) 1
        else if (tempShowNotDoneTasks) 2
        else 0
    var selectedOption by remember { mutableStateOf(radioOptions[initiallySelectedRadioOptionIndex]) }

    var dateToEdit by remember { mutableIntStateOf(0) }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
        )
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.95F)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
//                        .padding(paddingValues) // 10.dp
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(text = "Szűrők", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "1. Listázza a következőket:", style = MaterialTheme.typography.titleMedium,)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    radioOptions.forEach { optionName ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(0.33F)) {
                            RadioButton(
                                selected = (optionName == selectedOption),
                                onClick = { selectedOption = optionName }
                            )
                            Text(
                                text = optionName,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "2. Két időpont közötti elemek:", style = MaterialTheme.typography.titleMedium,)

                    Checkbox(checked = tempFilterByDateInterval, onCheckedChange = {
                        tempFilterByDateInterval = !tempFilterByDateInterval
                    })
                }
                if (tempFilterByDateInterval) {
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                        Row(
                            modifier = Modifier
                                .height(54.dp)
                                .weight(1F)
                                .clip(RoundedCornerShape(15.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(15.dp)
                                )
                                .clickable {
                                    dateToEdit = 0
                                    isDatePickerVisible = true
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = tempFormattedFilterStartMillis)
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline)
                        }

                        Row(
                            modifier = Modifier
                                .height(54.dp)
                                .weight(1F)
                                .clip(RoundedCornerShape(15.dp))
                                .border(
                                    1.dp,
                                    MaterialTheme.colorScheme.outline,
                                    RoundedCornerShape(15.dp)
                                )
                                .clickable {
                                    dateToEdit = 1
                                    isDatePickerVisible = true
                                }
                                .padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = tempFormattedFilterEndMillis)
                            Icon(imageVector = Icons.Default.DateRange, contentDescription = null,
                                tint = MaterialTheme.colorScheme.outline)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Spacer(Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            onClearFilters()
                            onDismiss()
                        },
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )) {
                        Icon(imageVector = Icons.Default.FilterAltOff, contentDescription = null)
                    }
                    OutlinedButton(onClick = onDismiss) {
                        Text("Mégse")
                    }
                    Button(onClick = {
                        if (selectedOption == radioOptions[0]) {
                            tempShowDoneTasks = true
                            tempShowNotDoneTasks = true
                        } else if (selectedOption == radioOptions[1]) {
                            tempShowDoneTasks = true
                            tempShowNotDoneTasks = false
                        } else if (selectedOption == radioOptions[2]) {
                            tempShowDoneTasks = false
                            tempShowNotDoneTasks = true
                        }

                        onConfirm(
                            tempShowDoneTasks,
                            tempShowNotDoneTasks,
                            tempFilterByDateInterval,
                            tempFilterStartMillis,
                            tempFilterEndMillis
                        )
                        onDismiss()
                    }) {
                        Text("Kész")
                    }
                }
            }


            if (isDatePickerVisible) {
                CustomDatePickerDialog(
                    onDateSelected = { formattedDate, calendar ->
                        if (dateToEdit == 0) {
                            tempFilterStartMillis = calendar.timeInMillis
                            tempFormattedFilterStartMillis = formattedDate
                        } else {
                            tempFilterEndMillis = calendar.timeInMillis
                            tempFormattedFilterEndMillis = formattedDate
                        }
                    },
                    dateFormatter = dateFormatter,
                    onDismiss = {
                        isDatePickerVisible = false
                    }
                )
            }
        }
    }
}