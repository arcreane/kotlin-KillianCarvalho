package com.example.fruitedmastermind

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
@Composable
fun GameScreen(viewModel: GameViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }
    var selectedCell by remember { mutableStateOf(0) }
    var currentGuess by remember { mutableStateOf(MutableList(4) { Fruit("", false, false, 0) }) }
    viewModel.start_game()
    Column {
        for (fruit in viewModel.combination_to_guess.value!!)
        {
            Text(text = fruit.name)
        }
        Row {
            Image(
                painter = painterResource(R.drawable.banana),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.banana),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.banana),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.banana),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Image(
                painter = painterResource(R.drawable.grape),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.grape),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.grape),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(R.drawable.grape),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(70.dp)
            )
        }
        var combination = listOf<String>("Banana", "Raspberry", "Orange", "Plum")
    }
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            for (i in 0 until 4) {
                Button(onClick = {
                    selectedCell = i
                    showDialog = true
                }) {
                    Text(text = currentGuess[i].name)
                }
            }
            Button(onClick = {
                viewModel.make_guess(currentGuess)
                currentGuess = MutableList(4) { Fruit("", false, false, 0) }
            }) {
                Text(text = "Send")
            }
        }
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Cellule $selectedCell") },
                text = {
                    Column {
                        for (fruit in viewModel.all_fruits) {
                            Button(onClick = {
                                currentGuess[selectedCell] = fruit
                                showDialog = false
                            }) {
                                Text(text = "${fruit.name}")
                            }
                        }
                    }
                },
                confirmButton = {  }
            )
        }
    }
}
