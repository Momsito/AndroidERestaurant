package fr.isen.ounes.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.isen.ounes.androiderestaurant.ui.theme.AndroidERestaurantTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DroidRestaurantApp()
                }
            }
        }
    }
}

@Composable
fun DroidRestaurantApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bienvenue chez",
            style = MaterialTheme.typography.
        )
        Spacer(modifier = Modifier.height(16.dp))
        ChoiceItem(text = "Entrée")
        Spacer(modifier = Modifier.height(8.dp))
        ChoiceItem(text = "Plat")
        Spacer(modifier = Modifier.height(8.dp))
        ChoiceItem(text = "Dessert")
    }
}

@Composable
fun ChoiceItem(text: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(4.dp))
            .height(40.dp),
        color = Color.LightGray
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterStart),
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DroidRestaurantAppPreview() {
    AndroidERestaurantTheme {
        DroidRestaurantApp()
    }
}
