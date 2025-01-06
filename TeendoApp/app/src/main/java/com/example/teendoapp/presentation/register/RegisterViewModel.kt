package com.example.teendoapp.presentation.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teendoapp.data.model.Response
import com.example.teendoapp.data.repository.AuthenticationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthenticationRepository
): ViewModel() {
    var uiState by mutableStateOf(RegisterUiState())
        private set

    fun onEvent(event: RegisterUiEvent) {
        when (event) {
            is RegisterUiEvent.EmailChangedEvent -> {
                uiState = uiState.copy(email = event.email)
            }
            is RegisterUiEvent.PasswordChangedEvent -> {
                uiState = uiState.copy(password = event.password)
            }
            is RegisterUiEvent.SubmitEvent -> {
                if (event.email.isNotEmpty() && event.password.isNotEmpty())
                    register(event.email, event.password)
            }
        }
    }

    private fun register(email: String, password: String) = viewModelScope.launch {
        uiState = uiState.copy(isSignUpLoading = true)
        repository.signUpUserWithEmailAndPassword(email, password).collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(isSignUpSuccessful = true)
                    sendEmailVerification()
                }
                is Response.Failure -> {
                    uiState = response.e.message?.let {
                        uiState.copy(signUpErrorMessage = it)
                    }!!
                }
                else -> {}
            }
        }
    }

    private fun sendEmailVerification() = viewModelScope.launch {
        uiState = uiState.copy(isSendingVerificationEmailInProgress = true)

        repository.sendEmailVerification().collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = uiState.copy(isVerificationEmailSentSuccessfully = true)
                }
                is Response.Failure -> {
                    uiState = response.e.message?.let {
                        uiState.copy(verificationEmailErrorMessage = it)
                    }!!
                }
                else -> {}
            }
        }
    }
}