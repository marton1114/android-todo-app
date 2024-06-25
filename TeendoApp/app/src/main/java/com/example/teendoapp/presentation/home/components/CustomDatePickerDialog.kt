package com.example.teendoapp.presentation.home.components

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.TimeZone

@ExperimentalMaterial3Api
@Composable
fun CustomDatePickerDialog(
    onDateSelected: (formattedDate: String, calendar: Calendar) -> Unit,
    dateFormatter: SimpleDateFormat,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    val calendar = Calendar.getInstance(TimeZone.getDefault())
    if (datePickerState.selectedDateMillis != null) {
        calendar.timeInMillis = datePickerState.selectedDateMillis!!
    } else {
        calendar.timeInMillis = System.currentTimeMillis()
    }
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)

    val formattedDate = dateFormatter.format(calendar.time)

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = {
                    onDateSelected(formattedDate, calendar)
                    onDismiss()
                }
            ) {
                Text(text = "Kész")
            }
        },
        dismissButton = {
            OutlinedButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Mégse")
            }
        },
        colors = DatePickerDefaults.colors().copy(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}