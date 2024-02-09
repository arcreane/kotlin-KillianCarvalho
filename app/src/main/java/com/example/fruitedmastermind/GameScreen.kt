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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(viewModel: GameViewModel, navController: NavController) {
    val showDialog = remember { mutableStateOf(false) }
    val selectedCell = remember { mutableStateOf(0) }
    val currentGuess = remember { mutableStateOf(MutableList(4) { Fruit("", false, false, 0) }) }
    val remaining_attempts by viewModel.remaining_attempts.observeAsState()
    val guess_history by viewModel.guess_history.observeAsState(emptyList())
    val result_history by viewModel.result_history.observeAsState(emptyList())
    /* TEST*/
    var presses by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                    }
                },
                title = {
                    Text("MasterMind")
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)

                ) {
                    InputRow(viewModel, currentGuess, showDialog, selectedCell)

                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { presses++ }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HistoryDisplay(guess_history = guess_history, result_history)
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
                        contentDescription = "${currentGuess.value[i].name}",
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
