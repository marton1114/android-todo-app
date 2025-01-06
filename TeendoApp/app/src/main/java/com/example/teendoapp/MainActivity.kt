package com.example.teendoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.teendoapp.data.repository.AuthenticationRepository
import com.example.teendoapp.data.repository.AuthenticationRepositoryImpl
import com.example.teendoapp.presentation.home.HomeScreen
import com.example.teendoapp.presentation.navigation.Route
import com.example.teendoapp.presentation.navigation.TeendoNavHost
import com.example.teendoapp.ui.theme.TeendoAppTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navHostController: NavHostController

    @Inject
    lateinit var authRepository: AuthenticationRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TeendoAppTheme {
                navHostController = rememberNavController()

                TeendoNavHost(navHostController)

                if (authRepository.isUserSignedIn()) {
                    navHostController.navigate(Route.HOME)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

    }
}
