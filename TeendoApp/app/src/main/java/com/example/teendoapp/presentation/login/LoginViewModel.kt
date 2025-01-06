package com.example.teendoapp.presentation.login


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
class LoginViewModel @Inject constructor(
    private val repository: AuthenticationRepository
): ViewModel() {
    var uiState by mutableStateOf(LoginUiState())
        private set

    init {
        uiState = uiState.copy(isSignInSuccessful = isUserLoggedIn())
    }

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.EmailChangedEvent -> {
                uiState = uiState.copy(email = event.email)
            }
            is LoginUiEvent.PasswordChangedEvent -> {
                uiState = uiState.copy(password = event.password)
            }
            is LoginUiEvent.SubmitEvent -> {
                if (uiState.email.isNotEmpty() && uiState.password.isNotEmpty())
                    login(uiState.email, uiState.password)
            }
        }
    }

    private fun login(email: String, password: String) = viewModelScope.launch {
        uiState = LoginUiState(isLoading = true)

        repository.signInUserWithEmailAndPassword(email, password).collect { response ->
            when (response) {
                is Response.Success -> {
                    uiState = LoginUiState(isSignInSuccessful = true)
                }
                is Response.Failure -> {
                    uiState = LoginUiState(signInErrorMessage = response.e.message!!)
                }
                else -> {}
            }
        }
    }

    private fun isUserLoggedIn() = repository.isUserSignedIn()
}