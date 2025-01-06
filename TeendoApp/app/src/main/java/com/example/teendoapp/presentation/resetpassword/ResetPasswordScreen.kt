package com.example.teendoapp.presentation.resetpassword

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.teendoapp.presentation.components.EmailTextField
import com.example.teendoapp.presentation.components.TopAppBarWithNavigationBack

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    navigateBack: () -> Unit
){
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isEmailSentSuccessfully) {
        if (uiState.isEmailSentSuccessfully) {
            navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithNavigationBack(
                title = "Jelszó frissítése",
                onClick = {
                    navigateBack()
                }
            )
        },
        contentWindowInsets = WindowInsets.ime
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(paddingValues)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                EmailTextField(uiState.email) {
                    viewModel.onEvent(ResetPasswordUiEvent.EmailChangedEvent(it))
                }
                Text(text = uiState.emailSendingErrorMessage, color = Color.Red,
                    modifier = Modifier.padding(10.dp, 0.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(
                            ResetPasswordUiEvent.SendPasswordResetEmailEvent(uiState.email)
                        )
                    }
                ) {
                    Text(text = "Jelszó frissítése")
                }
            }
        }
    }
}