import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fruitedmastermind.GameViewModel

@Composable
fun Home_screen(viewModel: GameViewModel, nav_controller: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Bienvenue dans le jeu Mastermind !")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            viewModel.start_game()
            nav_controller.navigate("game")
        }) {

            Text(text = "Commencer le jeu")
        }
    }
}