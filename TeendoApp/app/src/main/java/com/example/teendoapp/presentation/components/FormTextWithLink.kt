package com.example.teendoapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun FormTextWithLink(
    text: String,
    linkText: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = text, textAlign = TextAlign.Center)
        Text(
            text = linkText,
            modifier = Modifier
                .clickable {
                    onClick()
                },
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline
        )
    }
}