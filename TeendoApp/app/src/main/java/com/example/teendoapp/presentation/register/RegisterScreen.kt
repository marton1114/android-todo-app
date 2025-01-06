package com.example.teendoapp.presentation.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import com.example.teendoapp.presentation.components.FormTextWithLink
import com.example.teendoapp.presentation.components.PasswordTextField
import com.example.teendoapp.presentation.components.TopAppBarWithNavigationBack

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = hiltViewModel(),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isSendingVerificationEmailInProgress) {
        if (uiState.isSendingVerificationEmailInProgress) {
            navigateBack()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBarWithNavigationBack(
                title = "Regisztráció",
                onClick = { navigateBack() }
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailTextField(uiState.email) {
                    viewModel.onEvent(RegisterUiEvent.EmailChangedEvent(it))
                }
                PasswordTextField(uiState.password) {
                    viewModel.onEvent(RegisterUiEvent.PasswordChangedEvent(it))
                }
                Text(text = uiState.signUpErrorMessage, color = Color.Red,
                    modifier = Modifier.padding(10.dp, 0.dp))
                Button(
                    onClick = {
                        viewModel.onEvent(
                            RegisterUiEvent.SubmitEvent(uiState.email, uiState.password)
                        )
                    }
                ) {
                    Text(text = "Regisztrálás", style = MaterialTheme.typography.titleLarge)
                }
                FormTextWithLink("Már van felhasználód?",
                    "Jelentkezz be!") {
                    navigateBack()
                }
            }
        }
    }
}