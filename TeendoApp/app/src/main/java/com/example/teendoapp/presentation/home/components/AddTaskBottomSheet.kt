package com.example.teendoapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.teendoapp.ui.theme.importanceColors
import com.example.teendoapp.ui.theme.importanceTextColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,

    taskTitleIdValue: String,
    onTaskTitleIdValueChange: (newValue: String) -> Unit,

    taskTitleValue: String,
    onTaskTitleValueChange: (newValue: String) -> Unit,

    taskDescriptionValue: String,
    onTaskDescriptionValueChange: (newValue: String) -> Unit,

    startDateValue: String,
    endDateValue: String,

    importanceValue: Int,
    onImportanceValueChange: (newValue: Int) -> Unit,

    onStartDateClick: () -> Unit,
    onEndDateClick: () -> Unit,

    onAddButtonClick: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        dragHandle = null,
//        shape = RectangleShape,
        sheetState = sheetState,
        windowInsets = WindowInsets.ime
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onDismissRequest) {
                    Icon(imageVector = Icons.Rounded.Close, contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary)
                }
                Text(text = "Teendő hozzáadása", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    FilledIconButton(
                        onClick = {
                            onAddButtonClick()
                            onDismissRequest()
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row {
                            Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(6.dp))

            Text(text = "Sorszám és teendő (Röviden)", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                OutlinedTextField(value = taskTitleIdValue, onValueChange = onTaskTitleIdValueChange,
                    modifier = Modifier.width(70.dp), shape = RoundedCornerShape(15.dp),
                    singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number) )
                OutlinedTextField(value = taskTitleValue, onValueChange = onTaskTitleValueChange,
                    modifier = Modifier.weight(1F), shape = RoundedCornerShape(15.dp))
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Leírás (opcionális)", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
            OutlinedTextField(value = taskDescriptionValue, onValueChange = onTaskDescriptionValueChange,
                modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(15.dp))
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Aktualitás kezdete és határidő", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier
                        .height(54.dp)
                        .weight(1F)
                        .clip(RoundedCornerShape(15.dp))
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(15.dp))
                        .clickable { onStartDateClick() }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = startDateValue)
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
                        .clickable { onEndDateClick() }
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = endDateValue)
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = null,
                        tint = MaterialTheme.colorScheme.outline)
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Haladás", style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold)
            val interactionSource = remember {
                MutableInteractionSource()
            }
            Slider(
                value = importanceValue.toFloat(),
                onValueChange = { onImportanceValueChange(it.toInt()) },
                modifier = Modifier.fillMaxWidth(),
                valueRange = 1.0F..10.0F,
                steps = 8,
                thumb = {
                    Box {
                        SliderDefaults.Thumb(
                            interactionSource = interactionSource,
                            thumbSize = DpSize(42.dp, 42.dp),
                            colors = SliderDefaults.colors().copy(
                                thumbColor = importanceColors[importanceValue - 1]
                            ),
                        )
                        Text(text = importanceValue.toString(), style = MaterialTheme.typography.titleMedium, modifier = Modifier.align(Alignment.Center),
                            color = importanceTextColors[importanceValue - 1], fontWeight = FontWeight.Bold)
                    }
                },
                track = { sliderPositions ->
                    SliderDefaults.Track(
                        sliderState = sliderPositions,
                        modifier = Modifier
                            .scale(scaleX = 1f, scaleY = 3f)
                            .clip(CircleShape),
                        colors = SliderDefaults.colors().copy(
                            activeTrackColor = importanceColors[importanceValue - 1]
                        ),
                    )
                },
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}