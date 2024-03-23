package fr.isen.ounes.androiderestaurant


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.GsonBuilder
import fr.isen.ounes.androiderestaurant.model.Category
import fr.isen.ounes.androiderestaurant.model.Dish
import fr.isen.ounes.androiderestaurant.model.MenuResponse
import fr.isen.ounes.androiderestaurant.model.NetworkConstants
import fr.isen.ounes.androiderestaurant.ui.theme.AndroidERestaurantTheme
import org.json.JSONObject

val mamamia2 =  FontFamily(Font(R.font.mamami))

class MenuActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val type = (intent.getSerializableExtra(CATEGROY_EXTRA_KEY) as? DishType) ?: DishType.STARTER
        setContent {
            AndroidERestaurantTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = customBackgroundColor // Changement de la couleur de fond en beige
                ) {
                    MenuView(type)
                }
            }
        }
    }
    companion object {
        val CATEGROY_EXTRA_KEY = "CATEGROY_EXTRA_KEY"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuView(type: DishType) {
    val category = remember {
        mutableStateOf<Category?>(null)
    }
    Column(Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Text(type.title(),
            textAlign = TextAlign.Center,
            fontFamily =  mamamia,
            style = TextStyle(fontSize= 36.sp),
            color = Color.Red,
            modifier = Modifier.padding(20.dp)
            )

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
            category.value?.let {
                items(it.items) {
                    dishRow(it)
                }
            }
        }
    }
    PostData(type, category)
}

@Composable
fun dishRow(dish: Dish) {
    val context = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(customBackgroundColor), // Couleur de fond personnalisée
        border = BorderStroke(3.dp, color = divColor), // Couleur de la bordure personnalisée
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DISH_EXTRA_KEY, dish)
                context.startActivity(intent)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            // Emplacement dédié à l'image à gauche
            Box(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(40)) // Coins arrondis de l'image
                    .padding(5.dp)
                    .border(BorderStroke(2.dp, color = Color.Red)) // Bordure rouge
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(dish.images.first())
                        .build(),
                    placeholder = painterResource(R.drawable.noimage),
                    error = painterResource(R.drawable.noimage),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = dish.name,
                textAlign = TextAlign.Center,
                fontFamily = mamamia, // Utilisation de la police personnalisée
                style = TextStyle(fontSize = 22.sp),
                color = Color.Red,
                modifier = Modifier.weight(1f)
                    .padding(3.dp)// Permet de centrer le texte verticalement
            )
            // Emplacement dédié au prix à droite
            Text(
                text = "${dish.prices.first().price} €",
                textAlign = TextAlign.Right,
                fontFamily = mamamia, // Utilisation de la police personnalisée
                style = TextStyle(fontSize = 20.sp),
                color = Color.Black,
                modifier = Modifier.padding(end = 5.dp)
            )
        }
    }
}

@Composable
fun PostData(type: DishType, category: MutableState<Category?>) {
    val currentCategory = type.title()
    val context = LocalContext.current
    val queue = Volley.newRequestQueue(context)

    val params = JSONObject()
    params.put(NetworkConstants.ID_SHOP, "1")

    val request = JsonObjectRequest(
        Request.Method.POST,
        NetworkConstants.URL,
        params,
        { response ->
            val result = GsonBuilder().create().fromJson(response.toString(), MenuResponse::class.java)
            val filteredResult = result.data.first { categroy -> categroy.name == currentCategory }
            category.value = filteredResult
        },
        {
        }
    )
    queue.add(request)

}
