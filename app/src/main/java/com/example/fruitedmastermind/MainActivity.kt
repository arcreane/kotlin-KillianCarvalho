package com.example.fruitedmastermind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    }
}
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FruitedMastermindTheme {
        Greeting("Android")
    }
}