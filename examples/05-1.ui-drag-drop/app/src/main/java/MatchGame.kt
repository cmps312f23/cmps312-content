package ui.dragdrop

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ui.theme.AppTheme

data class Pet(val id: Int, val name: String, @DrawableRes val image: Int, var matched: Boolean = false)

@Composable
fun MatchGame() {
    val pets = remember {
        mutableStateListOf(
            Pet(1, "Cat", R.drawable.cat),
            Pet(2, "Fish", R.drawable.fish),
            Pet(3, "Rabbit", R.drawable.rabbit)
        )
    }

    val shuffledPets = remember {
        pets.shuffled().toMutableStateList()
    }

    LongPressDraggable(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            PetNameList(shuffledPets)
            PetsList(pets, shuffledPets, onDrop = {
                var index = pets.indexOfFirst { p -> p.id == it }
                println("Pet: index $index")
                if (index >= 0) {
                    pets[index] = pets[index].copy(matched = true)
                }

                index = shuffledPets.indexOfFirst { p -> p.id == it }
                println("shuffledPets: index $index")
                if (index >= 0) {
                    shuffledPets[index] = shuffledPets[index].copy(matched = true)
                }
            })

            Button(onClick = {
                for(idx in 0 until pets.size) {
                    pets[idx] = pets[idx].copy(matched = false)
                    shuffledPets[idx] = shuffledPets[idx].copy(matched = false)
                }
            }) {
                Text(text = "Reset", modifier = Modifier
                    .fillMaxHeight(0.1f))
            }

       }
    }
}

@Composable
fun PetNameList(pets: List<Pet>) {
    LazyRow(
        modifier = Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center //Arrangement.spacedBy(16.dp)
    ) {
        items(pets) {
            if (!it.matched) {
                DragTarget(dataToDrop = it, modifier = Modifier.padding(6.dp)) {
                    SuggestionChip(onClick = { /*TODO*/ },
                        label = { Text(text = it.name) })
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetsList(pets: List<Pet>, shuffledPets: List<Pet>, onDrop: (petIndex: Int)->Unit) {
    LazyRow(
        modifier = Modifier
            .fillMaxHeight(0.6f)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center //.spacedBy(8.dp)
    ) {
        items(pets) {

            DropTarget<Pet>(
                modifier = Modifier.padding(6.dp)
            ) { isInBound, pet ->
                val bgColor = if (isInBound) {
                    Color.Yellow
                } else {
                    Color.White
                }

                println("Pet: $pet")

                if (pet != null) {
                    if (isInBound && pet.id == it.id) {
                        onDrop(it.id)
                        /*println("Pet: isInBound $isInBound")
                        println("Pet: $pet")
                        println("Pet: it $it")*/
                    }
                }

                PetCard(pet = it, bgColor = bgColor)
            }
        }
    }
}

@Composable
fun PetCard(pet: Pet, bgColor: Color) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
        .background(bgColor, RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = pet.image),
            contentDescription = pet.name,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(120.dp)
        )
        if (pet.matched) {
            Text(text = pet.name,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth() )
        }
    }
}

@Preview
@Composable
fun MatchGamePreview() {
    AppTheme {
        MatchGame()
    }
}