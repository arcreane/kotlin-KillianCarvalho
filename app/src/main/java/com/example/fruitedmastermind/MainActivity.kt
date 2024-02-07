package com.example.fruitedmastermind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fruitedmastermind.ui.theme.FruitedMastermindTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

val fruits = listOf("Strawberry", "Banana", "Raspberry", "Kiwi", "Orange", "Plum", "Grape", "Lemon")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FruitedMastermindTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MasterMind()
                }
            }
        }
    }
}

// shuffle the list to randomize after that takes the 4th first element.
fun set_combination(): List<String> {
    val combination = fruits.shuffled().take(4)

    return combination
}

fun check_combination(guess_combination:List<String>, user_combination: List<String>): MutableList<Char>
{
    var nbr_good_position = 0
    var nbr_bad_place = 0
    var result_proposition: MutableList<Char> = mutableListOf()
    var cnt:Int = 0

    for (fruit in user_combination)
    {
        val check_value = guess_combination.find { it == fruit }
        if (check_value == null) {
            result_proposition.add('X')
            continue
        }
        else {
            if (guess_combination[cnt] == user_combination[cnt])
            {
                result_proposition.add('1')
            }
            else
                result_proposition.add('0')
        }
    }
    return result_proposition
}

@Composable
fun user_input() {
    var proposition: MutableList<String>
    var showDialog by remember { mutableStateOf(false) }
    var selectedCell by remember { mutableStateOf(0) }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        for (i in 0 until 4) {
            Button(onClick = {
                selectedCell = i
                showDialog = true
                println("case cliquable")
            }){}
        }
        Button(onClick = { println("case cliquable") }){
            Text(text = "Send")
            var modifier = Modifier
                .border(border = BorderStroke(width = 1.dp, Color.LightGray))
                .height(70.dp)
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Cellule $selectedCell")},
            text = {
                Column {
                    for (fruit in fruits) {
                        Button(onClick = { println("$fruit") }) {
                            Text(text = "$fruit")
                        }
                    }
                }
            },
            confirmButton = {  }
        )
    }
}

@Composable
fun MasterMind() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "HomeScreen") {
        composable("HomeScreen") {HomeScreen(navController)}
        composable("GameScreen") { GameScreen() }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Button(onClick = { navController.navigate("GameScreen") }) {
        Text("Play")
    }
}

@Preview
@Composable
fun GameScreen() {
    val fruits_to_guess = set_combination()
    Column {
        for (fruit in fruits_to_guess)
        {
            Text(text = fruit)
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
}
