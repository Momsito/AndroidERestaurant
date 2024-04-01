package fr.isen.ounes.androiderestaurant


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.ounes.androiderestaurant.ui.theme.AndroidERestaurantTheme
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign

val mamamia =  FontFamily(Font(R.font.mamami))
val customBackgroundColor = Color(0xFFF5F5DC) // Couleur beige en utilisant la valeur HEX
val divColor = Color(0xff2f9b57) // Couleur beige en utilisant la valeur HEX

enum class DishType {
    STARTER, MAIN, DESSERT;

    @Composable
    fun title(): String {
        return when(this) {
            STARTER -> stringResource(id = R.string.menu_starter)
            MAIN -> stringResource(id = R.string.menu_main)
            DESSERT -> stringResource(id = R.string.menu_dessert)
        }
    }
}

interface MenuInterface {
    fun dishPressed(dishType: DishType)
}

class HomeActivity : ComponentActivity(), MenuInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidERestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = customBackgroundColor // Changement de la couleur de fond en beige
                ) {
                    MenuView(this)
                }
            }
        }
    }

    override fun dishPressed(dishType: DishType) {
        val intent = Intent(this, MenuActivity::class.java)
        intent.putExtra(MenuActivity.CATEGROY_EXTRA_KEY, dishType)
        startActivity(intent)
    }
}

@Composable
fun MenuView(menu: MenuInterface) {
    Scaffold ( containerColor = customBackgroundColor ){
        Column(
            Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Benvenuto chez",
                fontFamily = mamamia,
                modifier = Modifier.padding(top = 25.dp),
                style = TextStyle(fontSize = 30.sp, fontStyle = FontStyle.Italic, color = Color.Red)
            )
            Image(
                painter = painterResource(id = R.drawable.icon),
                contentDescription = "Logo",
                modifier = Modifier.size(200.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,

                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 50.dp)
                        .background(color = Color.Red, RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp)) // Coins arrondis
                ) {
                    CustomButton(type = DishType.STARTER, menu)
                }
                Divider(
                    color = divColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp), // Remplir toute la largeur avec un padding horizontal
                    thickness = 3.dp
                )  // Changement de la couleur du séparateur en rouge
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 50.dp)
                        .background(color = Color.Red, RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp)) // Coins arrondis
                ) {
                    CustomButton(type = DishType.MAIN, menu)
                }
                Divider(
                    color = divColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp), // Remplir toute la largeur avec un padding horizontal
                    thickness = 3.dp
                ) // Changement de la couleur du séparateur en rouge
                Box(
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 150.dp, height = 50.dp)
                        .background(color = Color.Red, RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp)) // Coins arrondis
                ) {
                    CustomButton(type = DishType.DESSERT, menu)
                }
                Text(
                    "Contact : 07.83.05.69.34",
                    fontFamily = mamamia,
                    modifier = Modifier.padding(top = 40.dp),
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontStyle = FontStyle.Italic,
                        color = divColor
                    )
                )
                Text(
                    "Mail : notyourbuisness@gmail.com",
                    fontFamily = mamamia,
                    modifier = Modifier.padding(top = 10.dp),
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Italic,
                        color = divColor
                    )
                )

            }
        }
    }

}



@Composable
fun CustomButton(type: DishType, menu: MenuInterface) {
    TextButton(
        onClick = { menu.dishPressed(type) },
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Transparent)
    ) {
        Text(type.title(), fontFamily =  mamamia, style = TextStyle( fontSize= 17.sp), color = Color.White, textAlign = TextAlign.Center,  modifier = Modifier.fillMaxSize()  // Aligner le texte au centre
        ) // Changement de la couleur du texte en vert et en italique
    }
 }



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidERestaurantTheme {
        MenuView(HomeActivity())
    }
}