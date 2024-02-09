package com.example.fruitedmastermind

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
    val showWin = remember { mutableStateOf(false) }
    val showLoose = remember { mutableStateOf(false) }
    var showHints by remember { mutableStateOf(false) }

    val selectedCell = remember { mutableStateOf(0) }
    val currentGuess = remember { mutableStateOf(MutableList(4) { Fruit("", false, false, 0) }) }

    val remainingAttempts by viewModel.remaining_attempts.observeAsState()
    val guessHistory by viewModel.guess_history.observeAsState(emptyList())
    val resultHistory by viewModel.result_history.observeAsState(emptyList())
    val has_win by viewModel.win.observeAsState()
    val score by viewModel.score.observeAsState()


    Scaffold(
        topBar = {
            TopAppBar(colors = topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), navigationIcon = {
                IconButton(onClick = { navController.navigate("home") }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                }
            }, title = {
                Text("MasterMind")
            })
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
            Column {
                FloatingActionButton(onClick = { showHints = true }) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
                DropdownMenu(
                    expanded = showHints,
                    onDismissRequest = { showHints = false },

                    ) {
                    DropdownMenuItem(text = { Text("First hint -2 attempts") },
                        onClick = { viewModel.giveFirstHint() })
                    DropdownMenuItem(text = { Text("First hint -3 attempts") },
                        onClick = { viewModel.giveSecondHint() })
                }
            }

        },
    ) { innerPadding ->

        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Tentative left: $remainingAttempts")
        }
        Column(
            modifier = Modifier.padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            HistoryDisplay(guessHistory = guessHistory, resultHistory)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (remainingAttempts!! <= 0) {
            AlertDialogLoose(viewModel = viewModel, showLoose = showLoose)
        }
        if (has_win == true) AlertDialogWin(
            viewModel = viewModel,
            showWin = showWin,
            score = score
        )
        if (showDialog.value) {
            AlertDialogGuess(
                viewModel = viewModel,
                currentGuess = currentGuess,
                showWin = showDialog,
                selectedCell = selectedCell
            )
        }
    }
}


// composable to manage the choice of the user
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
                    }, contentAlignment = Alignment.Center
            ) {
                if (currentGuess.value[i].name == "") Text(text = currentGuess.value[i].name)
                else Image(
                    painter = painterResource(currentGuess.value[i].image),
                    contentDescription = currentGuess.value[i].name,
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

// Composable to manage list history
@Composable
fun HistoryDisplay(guessHistory: List<List<Fruit>>, resultHistory: List<List<Char>>) {
    LazyColumn {
        itemsIndexed(guessHistory) { index, guess ->
            Spacer(modifier = Modifier.height(20.dp))
            HistoryRow(fruitList = guess, resultList = resultHistory, index)
            Spacer(modifier = Modifier.height(20.dp))
            Divider()
        }
    }
}

// Composable to manage one row history
@Composable
fun HistoryRow(fruitList: List<Fruit>, resultList: List<List<Char>>, index: Int) {
    Row {
        for (fruit in fruitList) {
            Image(
                painter = painterResource(fruit.image),
                contentDescription = "Banane fruits",
                modifier = Modifier.size(60.dp)
            )
        }
        for (result in resultList[index]) {
            if (result == '1') Image(
                painter = painterResource(id = R.drawable.good_balise),
                contentDescription = "good place",
                modifier = Modifier.size(25.dp)
            )
            else if (result == '0') Image(
                painter = painterResource(id = R.drawable.bad_balise),
                contentDescription = "good place",
                modifier = Modifier.size(25.dp)
            )
            else Image(
                painter = painterResource(id = R.drawable.none_balise),
                contentDescription = "good place",
                modifier = Modifier.size(25.dp)
            )
        }
    }
}

// Composable to manage dialog guess which let appears the fruits list for the user
@Composable
fun AlertDialogGuess(
    viewModel: GameViewModel,
    currentGuess: MutableState<MutableList<Fruit>>,
    showWin: MutableState<Boolean>,
    selectedCell: MutableState<Int>
) {
    AlertDialog(onDismissRequest = { showWin.value = false },
        title = { Text(text = "CHOOSE A FRUIT") },
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3), contentPadding = PaddingValues(20.dp)
            ) {
                items(viewModel.all_fruits) { fruit ->
                    Image(painter = painterResource(id = fruit.image),
                        contentDescription = "good place",
                        modifier = Modifier
                            .size(90.dp)
                            .padding(10.dp)
                            .clickable {
                                currentGuess.value[selectedCell.value] = fruit
                                showWin.value = false
                            })
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        },
        confirmButton = { })
}

@Composable
fun AlertDialogWin(
    viewModel: GameViewModel,
    showWin: MutableState<Boolean>,
    score: Int?
) {
    AlertDialog(onDismissRequest = { showWin.value = false },
        title = { Text(text = "YOU HAVE WIN") },
        text = {
            Column {
                Text(text = "Good Job you have win with a score of $score ")
                Text(text = "Do you want to restart ?")
                Button(onClick = {
                    viewModel.resetGame()
                    viewModel.start_game()
                    showWin.value = false

                }) {
                    Text(text = "Play Again")
                }
            }

        },
        confirmButton = { })
}

@Composable
fun AlertDialogLoose(
    viewModel: GameViewModel,
    showLoose: MutableState<Boolean>,
) {
    AlertDialog(onDismissRequest = { showLoose.value = false },
        title = { Text(text = "GAME OVER") },
        text = {
            Column {
                Text(text = "Sorry You loose")
                Text(text = "Do you want to restart ?")
                Button(onClick = {
                    viewModel.resetGame()
                    viewModel.start_game()
                    showLoose.value = false

                }) {
                    Text(text = "Play Again")
                }
            }

        },
        confirmButton = { })
}
