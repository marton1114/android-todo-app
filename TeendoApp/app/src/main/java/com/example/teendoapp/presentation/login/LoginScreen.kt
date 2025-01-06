package com.example.teendoapp.presentation.login

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
import com.example.teendoapp.presentation.components.PasswordTextField
import com.example.teendoapp.presentation.components.TopAppBarSimple
import com.example.teendoapp.presentation.components.FormTextWithLink

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigateToResetPasswordScreen: () -> Unit,
    navigateToRegisterScreen: () -> Unit,
    navigateToHomeScreen: () -> Unit,
) {
    val uiState = viewModel.uiState

    LaunchedEffect(uiState.isSignInSuccessful) {
        if (uiState.isSignInSuccessful) {
            navigateToHomeScreen()
        }
    }

    Scaffold(
        topBar = { TopAppBarSimple(title = "Bejelentkezés") },
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
                EmailTextField(uiState.email) { viewModel.onEvent(LoginUiEvent.EmailChangedEvent(it)) }
                PasswordTextField(uiState.password) { viewModel.onEvent(LoginUiEvent.PasswordChangedEvent(it)) }
                Text(text = uiState.signInErrorMessage, color = Color.Red,
                    modifier = Modifier.padding(10.dp, 0.dp))
                Button(onClick = { viewModel.onEvent(LoginUiEvent.SubmitEvent) }) {
                    Text(text = "Bejelentkezés", style = MaterialTheme.typography.titleLarge)
                }
                FormTextWithLink("Elfelejtetted a jelszavad?", "Frissítsd jelszavad!") {
                    navigateToResetPasswordScreen()
                }
                FormTextWithLink("Még nincs fiókod?", "Regisztrálj!") {
                    navigateToRegisterScreen()
                }
            }
        }
    }
}