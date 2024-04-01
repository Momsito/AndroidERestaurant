package fr.isen.ounes.androiderestaurant

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.ounes.androiderestaurant.model.Dish
import kotlin.math.max


class DetailActivity : ComponentActivity() {
    @SuppressLint("SuspiciousIndentation")
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(dish_key) as? Dish
        setContent {
            val count = remember {
                mutableIntStateOf(1)
            }
            val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
            val pagerState = rememberPagerState(pageCount = {
                dish?.images?.count() ?: 0
            })

                Surface(modifier = Modifier.fillMaxSize(),
                    color = customBackgroundColor // Changement de la couleur de fond en beige
                ) {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            dish?.name ?: "",
                            textAlign = TextAlign.Center,
                            fontFamily = mamamia, // Utilisation de la police personnalisée
                            style = TextStyle(fontSize = 25.sp),
                            color = Color.Red,
                            modifier =  Modifier.padding(10.dp),
                        )


                        Divider(
                            color = divColor,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 40.dp), // Remplir toute la largeur avec un padding horizontal
                            thickness = 3.dp
                        )

                        HorizontalPager(state = pagerState) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(dish?.images?.get(it))
                                    .build(),
                                null,
                                placeholder = painterResource(R.drawable.noimage),
                                error = painterResource(R.drawable.noimage),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .height(200.dp)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            )
                        }
                        Divider(
                            color = divColor,
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 40.dp), // Remplir toute la largeur avec un padding horizontal
                            thickness = 3.dp
                        )
                        Text(
                            text = "Ingrédients :",
                            textAlign = TextAlign.Center,
                            fontFamily = mamamia, // Utilisation de la police personnalisée
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.Red,
                                    modifier =  Modifier.padding(12.dp),

                            )

                        Text(
                            ingredient,
                            textAlign = TextAlign.Center,
                            fontFamily = mamamia, // Utilisation de la police personnalisée
                            style = TextStyle(fontSize = 16.sp),
                            color = Color.Red,
                            modifier =  Modifier.padding(16.dp),
                            )
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.weight(1f))
                            OutlinedButton(onClick = {
                                count.value = max(1, count.value - 1)
                            }
                                ) {
                                Text("-",
                                    color = divColor,
                                    fontFamily = mamamia)
                            }
                            Text(count.value.toString(),
                                color = Color.Red,
                                        fontFamily = mamamia)
                            OutlinedButton(onClick = {
                                count.value = count.value + 1
                            }) {
                                Text("+",
                                    color = divColor,
                                    fontFamily = mamamia)
                            }
                            Spacer(Modifier.weight(1f))
                        }

                    }

                }

        }

    }
    companion object {
        val dish_key = "dish_key"
    }
}

