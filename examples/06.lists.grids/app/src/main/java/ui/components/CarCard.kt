package ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import model.CarRepo

@Composable
fun CarCard(carBrand: String, onItemClick: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor =  MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(carBrand) },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    )
    {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageLoader(CarRepo.getBrandLogoUrl(carBrand))
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = carBrand,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ImageLoader(url: String) {
    println("Getting image $url")
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "car logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(75.dp)
    )
}