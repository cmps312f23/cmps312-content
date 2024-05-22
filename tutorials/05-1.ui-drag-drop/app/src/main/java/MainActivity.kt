package ui.dragdrop

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    LongPressDraggable(modifier = Modifier.fillMaxSize()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(horizontal = 10.dp)
                        ) {
                            items(items = foodList) { food ->
                                FoodItemCard(foodItem = food)
                            }
                        }
                        PersonListContainer()
                    }
                }
            }
        }
    }
}

@Composable
fun BoxScope.PersonListContainer() {
    LazyRow(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .background(
                Color.LightGray,
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp)
            )
            .padding(vertical = 10.dp)
            .align(Alignment.BottomCenter),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        items(items = persons) { person ->
            PersonCard(person)
        }
    }

}

@Composable
fun PersonCard(person: Person) {
    val foodItems = remember {
        mutableStateMapOf<Int, FoodItem>()
    }

    DropTarget<FoodItem>(
        modifier = Modifier
            .padding(6.dp)
            .width(width = 120.dp)
            .fillMaxHeight(0.8f)
    ) { isInBound, foodItem ->
        val bgColor = if (isInBound) {
            Color.Red
        } else {
            Color.White
        }

        foodItem?.let {
            if (isInBound)
                foodItems[foodItem.id] = foodItem
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                .background(
                    bgColor,
                    RoundedCornerShape(16.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(id = person.profile), contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = person.name,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            if (foodItems.isNotEmpty()) {
                Text(
                    text = "$${foodItems.values.sumOf { it.price }}",
                    fontSize = 16.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "${foodItems.size} Items",
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun FoodItemCard(foodItem: FoodItem) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            DragTarget(modifier = Modifier.size(130.dp), dataToDrop = foodItem) {
                Image(
                    painter = painterResource(id = foodItem.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(16.dp))
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = foodItem.name,
                    fontSize = 22.sp,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "$${foodItem.price}",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }
    }
}