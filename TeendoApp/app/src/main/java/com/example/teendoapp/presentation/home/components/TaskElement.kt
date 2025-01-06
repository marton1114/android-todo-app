package com.example.teendoapp.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.example.teendoapp.data.model.Task
import com.example.teendoapp.ui.theme.importanceColors
import com.example.teendoapp.ui.theme.importanceTextColors

@Composable
fun TaskElement(
    modifier: Modifier = Modifier,
    task: Task,
    currentTimeMillis: Long,
    formattedCreationTime: String,
    formattedStartTime: String,
    formattedEndTime: String,
    formattedFinishTime: String,
    onDecrementProgression: () -> Unit,
    onIncrementProgression: () -> Unit,
    onPostponementButtonClick: () -> Unit,
    onDoneButtonClick: () -> Unit,
) {
//    val currentTimeMillis = System.currentTimeMillis()
    var isDescriptionVisible by remember { mutableStateOf(false) }
    val isForgotten by remember { mutableStateOf(task.endTime <= currentTimeMillis && !task.finished) }

    Card(
        onClick = { isDescriptionVisible = !isDescriptionVisible },
        elevation = CardDefaults.cardElevation(0.001.dp),
        border =
            if (isForgotten) {
                BorderStroke(2.dp, MaterialTheme.colorScheme.error)
            } else if (task.finished) {
                BorderStroke(2.dp, MaterialTheme.colorScheme.tertiary)
            } else {
                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
            },
        colors =
            if (isForgotten) {
                CardDefaults.cardColors().copy(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            } else if (task.finished) {
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            } else {
                CardDefaults.cardColors()
            },
        modifier = modifier.padding(10.dp, 5.dp, 10.dp, 0.dp)

//        modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.onSurface, RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                if (task.titleId.isNotEmpty() && task.titleId.isDigitsOnly()) {
                    Text(
                        text = "${task.titleId}.", style = MaterialTheme.typography.titleLarge,
                        color =
                        if (isForgotten)
                            MaterialTheme.colorScheme.error
                        else if (task.finished)
                            MaterialTheme.colorScheme.tertiary
                        else
                            MaterialTheme.colorScheme.primary,
                        modifier = Modifier.width(60.dp)
                    )
                }
                Text(text = task.title, style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textDecoration = if (task.finished) TextDecoration.LineThrough
                    else TextDecoration.None,
                    modifier = Modifier.weight(1F)
                )
            }
            Spacer(modifier = Modifier.height(6.dp))
            AnimatedVisibility(visible = isDescriptionVisible) {
                Column(modifier = Modifier.padding(5.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Létrehozás: ", fontWeight = FontWeight.Bold)
                        Text(text = formattedCreationTime, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(6.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Aktualitás kezdete: ", fontWeight = FontWeight.Bold)
                        Text(text = formattedStartTime, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(6.dp))
                    }
                    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Határidő: ", fontWeight = FontWeight.Bold)
                        Text(text = formattedEndTime, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(6.dp))
                    }
                    if (task.finished) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = "Befejezés: ", fontWeight = FontWeight.Bold)
                            Text(text = formattedFinishTime, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(6.dp))
                        }
                    }
                    if (! task.finished) {
                        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Text(text = "Haladás: ", fontWeight = FontWeight.Bold)
                            Text(text = "${task.progression}", textAlign = TextAlign.Center, modifier = Modifier
                                .clip(CircleShape)
                                .background(importanceColors[task.progression - 1])
                                .size(30.dp)
                                .padding(3.dp),
                                fontWeight = FontWeight.Bold, color = importanceTextColors[task.progression -1],)

                        }
                    }

                    if (task.description.trim().isNotEmpty()) {
                        Column(modifier = Modifier.fillMaxWidth(), ) {
                            Text(text = "Leírás:", fontWeight = FontWeight.Bold)
                            Text(text = task.description, modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(6.dp))
                        }
                    }

                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {

                if (!task.finished) {
                    AnimatedVisibility(visible = ! isDescriptionVisible) {
                        Row(verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            Text(text = "${task.progression}", textAlign = TextAlign.Center, modifier = Modifier
                                .clip(CircleShape)
                                .background(importanceColors[task.progression - 1])
                                .size(30.dp)
                                .padding(3.dp),
                                fontWeight = FontWeight.Bold, color = importanceTextColors[task.progression -1],)

                            Text(text = formattedEndTime, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.background)
                                .padding(6.dp))
                        }
                    }
                    AnimatedVisibility(visible = isDescriptionVisible) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            FilledIconButton(
                                onClick = onDecrementProgression,
                                colors =
                                if (isForgotten)
                                    IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError)
                                else
                                    IconButtonDefaults.filledIconButtonColors()
                            ) {
                                Icon(imageVector = Icons.Default.Remove, contentDescription = null)
                            }
                            FilledIconButton(
                                onClick = onIncrementProgression,
                                colors =
                                if (isForgotten)
                                    IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError)
                                else
                                    IconButtonDefaults.filledIconButtonColors()
                            ) {
                                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1F))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {

                        FilledIconButton(
                            onClick = onPostponementButtonClick,
                            colors =
                                if (isForgotten)
                                    IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError)
                                else
                                    IconButtonDefaults.filledIconButtonColors()
                        ) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        }
                        FilledIconButton(
                            onClick = onDoneButtonClick,
                            colors =
                                if (isForgotten)
                                    IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.error,
                                        contentColor = MaterialTheme.colorScheme.onError)
                                else
                                    IconButtonDefaults.filledIconButtonColors()
                        ) {
                            Icon(imageVector = Icons.Default.Done, contentDescription = null)
                        }
                    }
                } else {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Text(text = "Kész!", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(6.dp),
                            color = MaterialTheme.colorScheme.onTertiary)

                    }

                    Spacer(modifier = Modifier.weight(1F))
                    FilledIconButton(
                        onClick = onPostponementButtonClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                    }
                    FilledIconButton(
                        onClick = onDoneButtonClick,
                        colors = IconButtonDefaults.iconButtonColors(
                            containerColor = MaterialTheme.colorScheme.tertiary,
                            contentColor = MaterialTheme.colorScheme.onTertiary
                        )
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null)
                    }
                }
            }
        }
    }
}
