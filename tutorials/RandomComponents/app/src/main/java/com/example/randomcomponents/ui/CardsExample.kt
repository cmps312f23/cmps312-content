package com.example.randomcomponents.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.randomcomponents.MyApp
import com.example.randomcomponents.R
import com.example.randomcomponents.ui.theme.RandomComponentsTheme

@Composable
fun CardsExample() {
    // using card material3 design
    // * Card with content argument
    Column {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = {
                Text("Card with content argument",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelLarge)
            }
        )

        // * Card with shape argument
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //set shape of the card
            shape = RoundedCornerShape(16.dp),
            content = {
                Text("Card with shape argument", modifier = Modifier.padding(16.dp),style = MaterialTheme.typography.labelLarge)
            }
        )

        // * Card with background color argument
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //set background color of the card
            colors = CardDefaults.cardColors(
                containerColor =  MaterialTheme.colorScheme.primaryContainer,
            ),
            content = {
                Text("Card with background color argument", modifier = Modifier.padding(16.dp),style = MaterialTheme.typography.labelLarge)
            }
        )

        // * Card with elevation
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            //set card elevation of the card
            elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
            ),
            content = {
                Text("Card with background color argument", modifier = Modifier.padding(16.dp),style = MaterialTheme.typography.labelLarge)
            }
        )

        // * Card with border argument
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            border = BorderStroke(2.dp, Color.Black),
            content = {
                Text("Card with border argument", modifier = Modifier.padding(16.dp),style = MaterialTheme.typography.labelLarge)
            }
        )

        // * Complete Card Design with Image
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(8.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
            //set card elevation of the card
            elevation = CardDefaults.cardElevation(
                defaultElevation =  10.dp,
            ),
            colors = CardDefaults.cardColors(
                containerColor =  MaterialTheme.colorScheme.primaryContainer,
            ),
        ) {
            Column(modifier = Modifier.clickable(onClick = {  })) {

                Image(
                    painter = painterResource(R.drawable.sliced_lemons),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(150.dp)
                        .fillMaxWidth()
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Title",
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 2,
                        overflow = TextOverflow.Visible
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "Sub title Example code for android + composes app developers.",
                        //maxLines = 1,
                        //overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
            }
        }

    }
}


@Preview(showBackground = true , showSystemUi = true)
@Composable
fun CardsExamplePreview() {
    RandomComponentsTheme {
        CardsExample()
    }
}