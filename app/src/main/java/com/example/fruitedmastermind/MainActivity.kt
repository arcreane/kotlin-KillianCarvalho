package com.example.fruitedmastermind

import Home_screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fruitedmastermind.ui.theme.FruitedMastermindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FruitedMastermindTheme {
                val nav_Controller = rememberNavController()
                val viewModel = GameViewModel()

                NavHost(nav_Controller, startDestination = "home") {
                    composable("home") {Home_screen(viewModel = viewModel, nav_Controller)}
                    composable("game") { GameScreen(viewModel = viewModel, navController = nav_Controller)}
                }
            }
       }
    }
}