package com.example.teendoapp.presentation.register

data class RegisterUiState (
    val email: String = "",
    val password: String = "",

    val isSignUpLoading: Boolean = false,
    val isSignUpSuccessful: Boolean = false,
    val signUpErrorMessage: String = "",

    val isSendingVerificationEmailInProgress: Boolean = false,
    val isVerificationEmailSentSuccessfully: Boolean = false,
    val verificationEmailErrorMessage: String = ""
)