package com.example.teendoapp.presentation.home.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.teendoapp.ui.theme.importanceColors
import com.example.teendoapp.ui.theme.importanceTextColors
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PostponementDialog(
    titleId: String,
    title: String,
    description: String,
    startMillis: Long,
    endMillis: Long,
    importance: Int,
    dateFormatter: SimpleDateFormat,
    onDelete: () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (newTitleId: String, newTitle: String, newDescription: String,
                newStartMillis: Long, newEndMillis: Long,
                newImportance: Int) -> Unit,
) {
    var tempTitleId by remember { mutableStateOf(titleId) }
    var tempTitle by remember { mutableStateOf(title) }
    var tempDescription by remember { mutableStateOf(description) }
    var tempImportance by remember { mutableIntStateOf(importance) }
    var tempStartMillis by remember { mutableLongStateOf(startMillis) }
    var tempFormattedStartMillis by remember { mutableStateOf(dateFormatter.format(Date(tempStartMillis))) }
    var tempEndMillis by remember { mutableLongStateOf(endMillis) }
    var tempFormattedEndMillis by remember { mutableStateOf(dateFormatter.format(Date(tempEndMillis))) }

    var dateToEdit by remember { mutableIntStateOf(0) }
    var isDatePickerVisible by remember { mutableStateOf(false) }

    var isDeleteDialogVisible by remember { mutableStateOf(false) }


    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnBackPress = false
        ),
    )
    {
        Card(
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.95F),
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                Text(text = "Aktualitás elnapolása", style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(5.dp))

                Text(text = "Sorszám és teendő (Röviden)", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    OutlinedTextField(value = tempTitleId, onValueChange = { tempTitleId = it },
                        modifier = Modifier.width(70.dp), shape = RoundedCornerShape(15.dp),
                        singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) )
                    OutlinedTextField(value = tempTitle, onValueChange = { tempTitle = it },
                        modifier = Modifier.weight(1F), shape = RoundedCornerShape(15.dp))
                }
                Text(text = "Leírás (opcionális)", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = tempDescription, onValueChange = { tempDescription = it },
                    modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp))
                Spacer(modifier = Modifier.height(6.dp))

                Text(text = "Aktualitás kezdete és határidő", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
                        Text(text = tempFormattedStartMillis)
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
                        Text(text = tempFormattedEndMillis)
                        Icon(imageVector = Icons.Default.DateRange, contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline)
                    }
                }


                Text(text = "Haladás", style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth())
                val interactionSource = remember {
                    MutableInteractionSource()
                }
                Slider(
                    value = tempImportance.toFloat(),
                    onValueChange = { tempImportance = it.toInt() },
                    modifier = Modifier.fillMaxWidth(),
                    valueRange = 1.0F..10.0F,
                    steps = 8,
                    thumb = {
                        Box {
                            SliderDefaults.Thumb(
                                interactionSource = interactionSource,
                                thumbSize = DpSize(42.dp, 42.dp),
                                colors = SliderDefaults.colors().copy(
                                    thumbColor = importanceColors[tempImportance - 1]
                                ),
                            )
                            Text(text = tempImportance.toString(), style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Center),
                                color = importanceTextColors[tempImportance - 1], fontWeight = FontWeight.Bold)
                        }
                    },
                    track = { sliderPositions ->
                        SliderDefaults.Track(
                            sliderState = sliderPositions,
                            modifier = Modifier
                                .scale(scaleX = 1f, scaleY = 3f)
                                .clip(CircleShape),
                            colors = SliderDefaults.colors().copy(
                                activeTrackColor = importanceColors[tempImportance - 1]
                            ),
                        )
                    },
                )


                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    IconButton(
                        onClick = { isDeleteDialogVisible = true },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                    }
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(onClick = onDismiss) {
                        Text("Mégse")
                    }
                    Button(onClick = {
                        onConfirm(
                            tempTitleId,
                            tempTitle,
                            tempDescription,
                            tempStartMillis,
                            tempEndMillis,
                            tempImportance
                        )
                        onDismiss()
                    }) {
                        Text("Kész")
                    }
                }
            }
        }
    }

    if (isDatePickerVisible) {
        CustomDatePickerDialog(
            onDateSelected = { formattedDate, calendar ->
                if (dateToEdit == 0) {
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)

                    tempStartMillis = calendar.timeInMillis
                    tempFormattedStartMillis = formattedDate
                } else {
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)

                    tempEndMillis = calendar.timeInMillis
                    tempFormattedEndMillis = formattedDate
                }
            },
            dateFormatter = dateFormatter,
            onDismiss = {
                isDatePickerVisible = false
            }
        )
    }

    if (isDeleteDialogVisible) {
        AlertDialog(
            title = { Text(text = "Elem törlése") },
            text = { Text(text = "Biztos benne, hogy törölni szeretné az elemet?") },
            onDismissRequest = { isDeleteDialogVisible = false },
            dismissButton = {
                OutlinedButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = "Mégse")
                }
            },
            confirmButton = {
                Button(onClick = {
                    onDelete()
                    onDismiss()
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )) {
                    Text(text = "Törlés")
                }
            },
        )
    }
}

