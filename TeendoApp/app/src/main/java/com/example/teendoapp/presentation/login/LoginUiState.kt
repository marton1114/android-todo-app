package com.example.teendoapp.presentation.login

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    val isLoading: Boolean = false,
    val isSignInSuccessful: Boolean = false,
    val signInErrorMessage: String = "",
)