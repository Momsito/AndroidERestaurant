package fr.isen.ounes.androiderestaurant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import fr.isen.ounes.androiderestaurant.model.Dish
import kotlin.math.max

val hello = FontFamily(Font(R.font.mamami))

val background = Color(0xFFF5F5DC)
@Suppress("DEPRECATION")
class DetailActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dish = intent.getSerializableExtra(DISH_EXTRA_KEY) as? Dish
        setContent {
            val context = LocalContext.current
            val count = remember {
                mutableIntStateOf(1)
            }
            val ingredient = dish?.ingredients?.map { it.name }?.joinToString(", ") ?: ""
            val pagerState = rememberPagerState(pageCount = {
                dish?.images?.count() ?: 0
            })
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TopAppBar({
                    Text(dish?.name ?: "")
                })
                HorizontalPager(state = pagerState) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(dish?.images?.get(it))
                            .build(),
                        null,
                        placeholder = painterResource(R.drawable.noimage),
                        error = painterResource(R.drawable.noimage),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
                Text(ingredient)
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(Modifier.weight(1f))
                    OutlinedButton(onClick = {
                        count.value = max(1, count.value - 1)
                    }) {
                        Text("-")
                    }
                    Text(count.value.toString())
                    OutlinedButton(onClick = {
                        count.value = count.value + 1
                    }) {
                        Text("+")
                    }
                    Spacer(Modifier.weight(1f))
                }

                }

                }
            }
        companion object {
        val DISH_EXTRA_KEY = "DISH_EXTRA_KEY"
    }
}

