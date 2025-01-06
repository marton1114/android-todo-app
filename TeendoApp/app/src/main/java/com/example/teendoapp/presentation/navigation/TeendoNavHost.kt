package com.example.teendoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.teendoapp.presentation.home.HomeScreen
import com.example.teendoapp.presentation.login.LoginScreen
import com.example.teendoapp.presentation.register.RegisterScreen
import com.example.teendoapp.presentation.resetpassword.ResetPasswordScreen

@Composable
fun TeendoNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        route = "root",
        startDestination = Route.LOGIN,
        builder = {
            composable(route = Route.LOGIN) {
                LoginScreen(
                    navigateToResetPasswordScreen = {
                        navHostController.navigate(Route.RESET_PASSWORD)
                    },
                    navigateToRegisterScreen = {
                        navHostController.navigate(Route.REGISTER)
                    },
                    navigateToHomeScreen = {
                        navHostController.navigate(Route.HOME) {
                            popUpTo(Route.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(route = Route.REGISTER) {
                RegisterScreen(
                    navigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable(route = Route.RESET_PASSWORD) {
                ResetPasswordScreen(
                    navigateBack = {
                        navHostController.popBackStack()
                    }
                )
            }
            composable(route = Route.HOME) {
                HomeScreen()
            }
        }
    )
}