package com.example.teendoapp.presentation.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSimple(
    title: String
) {
    TopAppBar(
        title = {
            Text(text = title)
        }

    )
}

@Preview(showBackground = true, backgroundColor = 0xFF00FF00)
@Composable
fun SimpleTopAppBarPreview() {
    TopAppBarSimple("Test")
}