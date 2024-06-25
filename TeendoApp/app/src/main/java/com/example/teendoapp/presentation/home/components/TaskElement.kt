package com.example.teendoapp.presentation.home.components

import android.util.MutableBoolean
import android.widget.Space
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.DoubleArrow
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.teendoapp.data.model.Task
import com.example.teendoapp.ui.theme.importanceColors
import com.example.teendoapp.ui.theme.importanceTextColors

@Composable
fun TaskElement(
    task: Task,
    formattedStartTime: String,
    formattedEndTime: String,
    onPostponementButtonClick: () -> Unit,
    onDoneButtonClick: () -> Unit,
) {
    var isDescriptionVisible by remember { mutableStateOf(false) }
    Card(
        onClick = { isDescriptionVisible = !isDescriptionVisible },
//        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "${task.id}.", style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = task.title, style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold)

            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = formattedStartTime)
                Text(text = formattedEndTime)
            }

            Column(modifier = Modifier.padding(5.dp)) {
                Spacer(modifier = Modifier.height(5.dp))
                AnimatedVisibility(visible = isDescriptionVisible) {
                    Text(text = task.description)
                }
            }


            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {

                Text(text = " ${task.importance}.", modifier = Modifier
                    .clip(CircleShape)
                    .background(importanceColors[task.importance - 1])
                    .padding(7.5.dp),
                    fontWeight = FontWeight.Bold, color = importanceTextColors[task.importance -1],)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    FilledIconButton(
                        onClick = onPostponementButtonClick,
                        colors = IconButtonDefaults.iconButtonColors().copy(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        )
                    ) {
                        Icon(imageVector = Icons.Default.DoubleArrow, contentDescription = null)
                    }
                    FilledIconButton(onClick = onDoneButtonClick) {
                        Icon(imageVector = Icons.Default.Done, contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TaskElementPreview() {
    TaskElement(Task(0, "Rövid leírás", "slkfj sdélfk gjsdéfl " +
            "saklédfjaséldkj fasélkdjf aséldkjf aélsdkjf aélskdjf aélksdjf aé" +
            "asdkfnasdlkfnasdlkfjasldkfj asldkfj aslkdjf alskdjf alskdjf alks", 7),
        "2024. 01. 15. 15:00", "2024. 01. 15. 15:00", {}, {})
}