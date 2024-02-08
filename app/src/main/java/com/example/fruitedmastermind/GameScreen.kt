package com.example.fruitedmastermind

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun GameScreen(viewModel: GameViewModel, navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedCell = remember { mutableStateOf(0) }
    val currentGuess = remember { mutableStateOf(MutableList(4) { Fruit("", false, false, 0) }) }
    val remaining_attempts by viewModel.remaining_attempts.observeAsState()
    val guess_history by viewModel.guess_history.observeAsState(emptyList())
    val result_history by viewModel.result_history.observeAsState(emptyList())

    Column {

        for (fruit in viewModel.combination_to_guess.value!!) {
            Text(text = fruit.name)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (showDialog.value) {
            Alert_dialog_guess(
                viewModel = viewModel,
                currentGuess = currentGuess,
                showDialog = showDialog,
                selectedCell = selectedCell
            )
        }
        InputRow(viewModel, currentGuess, showDialog, selectedCell)
        HistoryDisplay(guess_history = guess_history, result_history)
    }
}

@Composable
fun InputRow(
    viewModel: GameViewModel,
    currentGuess: MutableState<MutableList<Fruit>>,
    showDialog: MutableState<Boolean>,
    selectedCell: MutableState<Int>
) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        for (i in 0 until 4) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .border(2.dp, Color.Black)
                    .background(Color.White)
                    .clickable {
                        showDialog.value = true
                        selectedCell.value = i
                    },
                contentAlignment = Alignment.Center
            ) {
                if (currentGuess.value[i].name == "")
                    Text(text = currentGuess.value[i].name)
                else
                    Image(
                        painter = painterResource(currentGuess.value[i].image),
                        contentDescription = "Banane fruits",
                        modifier = Modifier.size(50.dp)
                    )
            }
        }
        Button(onClick = {
            viewModel.make_guess(currentGuess.value)
            currentGuess.value = MutableList(4) { Fruit("", false, false, 0) }
        }) {
            Text(text = "Send")
        }
    }
}

@Composable
fun HistoryDisplay(guess_history: List<List<Fruit>>, result_history: List<List<Char>>) {
    Column {
        for (guess in guess_history) {
            Row {

                for (fruit in guess) {
                    Image(
                        painter = painterResource(fruit.image),
                        contentDescription = "Banane fruits",
                        modifier = Modifier.size(60.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                        .background(Color.Gray)
                )
                for (value in result_history) {
                    Text(text = value.toString())
                }
                Divider(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp)
                )
            }
        }
    }
}

@Composable
fun Alert_dialog_guess(
    viewModel: GameViewModel,
    currentGuess: MutableState<MutableList<Fruit>>,
    showDialog: MutableState<Boolean>,
    selectedCell: MutableState<Int>
) {
    AlertDialog(
        onDismissRequest = { showDialog.value = false },
        title = { Text(text = "Cellule ${selectedCell.value}") },
        text = {
            Column {
                for (fruit in viewModel.all_fruits) {
                    Button(onClick = {
                        currentGuess.value[selectedCell.value] = fruit
                        showDialog.value = false
                    }) {
                        Text(text = "${fruit.name}")
                    }
                }
            }
        },
        confirmButton = { }
    )
}
