package com.example.teendoapp.presentation.resetpassword

data class ResetPasswordUiState(
    val email: String = "",

    val isEmailSendingInProgress: Boolean = false,
    val isEmailSentSuccessfully: Boolean = false,
    val emailSendingErrorMessage: String = "",
)