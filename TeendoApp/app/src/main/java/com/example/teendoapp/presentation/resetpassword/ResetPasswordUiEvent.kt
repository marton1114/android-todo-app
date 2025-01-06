package com.example.teendoapp.presentation.resetpassword

interface ResetPasswordUiEvent {
    data class EmailChangedEvent(val email: String): ResetPasswordUiEvent
    data class SendPasswordResetEmailEvent(val email: String): ResetPasswordUiEvent
}