package com.example.fruitedmastermind

import Home_screen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val nav_Controller = rememberNavController()

            NavHost(nav_Controller, startDestination = "home") {
                composable("home") {Home_screen(nav_Controller)}
                composable("game") { GameScreen(viewModel = GameViewModel(), navController = nav_Controller)}
            }
       }
    }
}