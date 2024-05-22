package compose.ui.basics

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.ui.R
import compose.ui.ui.theme.ComposeUITheme

@Composable
fun ComposeLogoScreen() {
    Card () { //elevation = 10.dp
        var expanded by remember { mutableStateOf(false) }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded })
        {
            Image(painter = painterResource(id = R.drawable.img_compose_logo),
                contentDescription = "Jetpack compose logo",
                modifier = Modifier.height(300.dp))
            if (expanded) {
                Text(
                    text = "Jetpack Compose",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeLogoScreenPreview() {
    ComposeUITheme {
        ComposeLogoScreen()
    }
}