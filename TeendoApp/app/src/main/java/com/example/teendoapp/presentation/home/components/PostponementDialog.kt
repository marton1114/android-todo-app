package com.example.teendoapp.presentation.home.components

import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun PostponementDialog(
    onDismiss: () -> Unit,
    onConfirm: (dateValue: Int, dateValueType: Int, timeValue: Int, timeValueType: Int) -> Unit,
) {
    var dateValue by remember { mutableIntStateOf(0) }
    var dateDropdownExpanded by remember { mutableStateOf(false) }
    val dateList = listOf("Nap", "Hét", "Hónap")
    var selectedDateItem by remember { mutableStateOf(dateList[0]) }
    val dateIcon = if (dateDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var timeValue by remember { mutableIntStateOf(0) }
    var timeDropdownExpanded by remember { mutableStateOf(false) }
    val timeList = listOf("Óra", "Perc")
    var selectedTimeItem by remember { mutableStateOf(timeList[0]) }

    val timeIcon = if (timeDropdownExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
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
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Halasztás mértéke", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(10.dp))
//                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center) {
//                    Text(text = "2024. 01. 15 \n 14:00", modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)).padding(6.dp))
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Icon(imageVector = Icons.Default.KeyboardDoubleArrowRight, contentDescription = null)
//                    Spacer(modifier = Modifier.width(6.dp))
//                    Text(text = "2024. 01. 15 \n 14:00", modifier = Modifier.border(2.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)).padding(6.dp))
//                }

                Row(verticalAlignment = Alignment.CenterVertically, ) {
                    Row(modifier = Modifier.height(100.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.height(100.dp).width(100.dp).padding(3.dp)
                            .clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.background),) {
                            Text(text = dateValue.toString(), modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineLarge,
                            )
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween) {
                            FilledIconButton(onClick = {
                                dateValue++
                            }, modifier = Modifier
                                .size(50.dp)
                                .padding(3.dp),
                                shape = RoundedCornerShape(12.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null)
                            }
                            FilledIconButton(onClick = {
                                if (dateValue > 0) {
                                    dateValue--
                                }
                            }, modifier = Modifier
                                .size(50.dp)
                                .padding(3.dp),
                                shape = RoundedCornerShape(12.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    }

                    Column {
                        Row(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(3.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .clickable { dateDropdownExpanded = !dateDropdownExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedDateItem, modifier = Modifier.padding(6.dp), style = MaterialTheme.typography.headlineSmall)
                            Icon(imageVector = dateIcon, contentDescription = null, modifier = Modifier.padding(6.dp),)
                        }
                        DropdownMenu(
                            expanded = dateDropdownExpanded,
                            onDismissRequest = { dateDropdownExpanded = false },
                            modifier = Modifier
                                .width(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            dateList.forEach { label ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = label, style = MaterialTheme.typography.bodyLarge)
                                    },
                                    onClick = {
                                        selectedDateItem = label
                                        dateDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically, ) {
                    Row(modifier = Modifier.height(100.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.height(100.dp).width(100.dp).padding(3.dp)
                            .clip(RoundedCornerShape(12.dp)).background(MaterialTheme.colorScheme.background),) {
                            Text(text = timeValue.toString(), modifier = Modifier.align(Alignment.Center),
                                textAlign = TextAlign.Center, style = MaterialTheme.typography.headlineLarge,
                            )
                        }
                        Column(modifier = Modifier.fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween) {
                            FilledIconButton(onClick = {
                                timeValue++
                            }, modifier = Modifier
                                .size(50.dp)
                                .padding(3.dp),
                                shape = RoundedCornerShape(12.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropUp, contentDescription = null)
                            }
                            FilledIconButton(onClick = {
                                if (timeValue > 0) {
                                    timeValue--
                                }
                            }, modifier = Modifier
                                .size(50.dp)
                                .padding(3.dp),
                                shape = RoundedCornerShape(12.dp)) {
                                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = null)
                            }
                        }
                    }

                    Column {
                        Row(
                            modifier = Modifier
                                .size(100.dp)
                                .padding(3.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .clickable { timeDropdownExpanded = !timeDropdownExpanded },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = selectedTimeItem, modifier = Modifier.padding(6.dp), style = MaterialTheme.typography.headlineSmall)
                            Icon(imageVector = timeIcon, contentDescription = null, modifier = Modifier.padding(6.dp),)
                        }
                        DropdownMenu(
                            expanded = timeDropdownExpanded,
                            onDismissRequest = { timeDropdownExpanded = false },
                            modifier = Modifier
                                .width(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            timeList.forEach { label ->
                                DropdownMenuItem(
                                    text = {
                                        Text(text = label, style = MaterialTheme.typography.bodyLarge)
                                    },
                                    onClick = {
                                        selectedTimeItem = label
                                        timeDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }


                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(onClick = onDismiss) {
                        Text("Mégse")
                    }
                    Button(onClick = {
                        var dateValueType: Int = 0
                        var timeValueType: Int = 0
                        when (selectedDateItem) {
                            dateList[0] -> { dateValueType = Calendar.DAY_OF_YEAR }
                            dateList[1] -> { dateValueType = Calendar.WEEK_OF_YEAR }
                            dateList[2] -> { dateValueType = Calendar.MONTH }
                        }
                        when (selectedTimeItem) {
                            timeList[0] -> { timeValueType = Calendar.HOUR_OF_DAY }
                            timeList[1] -> { timeValueType = Calendar.MINUTE }
                        }

                        onConfirm(dateValue, dateValueType, timeValue, timeValueType)
                        onDismiss()
                    }) {
                        Text("Kész")
                    }
                }
            }
        }
    }
}

