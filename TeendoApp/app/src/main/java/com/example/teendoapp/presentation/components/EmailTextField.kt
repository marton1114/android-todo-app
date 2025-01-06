package com.example.teendoapp.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailTextField(
    value: String,
    onValueChange: (newValue: String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue -> onValueChange(newValue) },
        label = { Text(text = "Email") },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        )
    )
}