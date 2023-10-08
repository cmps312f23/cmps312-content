package ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import model.Surah
import ui.common.lightYellow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VersesList(surah: Surah?, onNavigateBack: () -> Unit) {
    /* Get an instance of the shared viewModel
    Make the activity the store owner of the viewModel
    to ensure that the same viewModel instance is used for all destinations
    */


    val verses = buildAnnotatedString {
        surah?.verses?.forEach { verse ->
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = 20.sp)
            ) {
                append("${verse.text}")
            }
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
                color = Color.Blue)) {
                append(" (${verse.id}) ")
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(text = "${surah?.name} (${surah?.ayaCount})",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                ) },
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                }
            )
        }
    ) {
        Card(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = lightYellow
            ),
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .border(
                    width = 2.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .verticalScroll(rememberScrollState())
        ) {
            Column {
                // Taoba do not display Basmala
                if (surah?.id != 9) {
                    Text(
                        text = "بِسۡمِ ٱللَّهِ ٱلرَّحۡمَٰنِ ٱلرَّحِيمِ",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp).fillMaxWidth(),
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Blue,
                            textDirection = TextDirection.Rtl
                        )
                    )
                }

                Text(
                    text = verses,
                    modifier = Modifier.padding(8.dp).fillMaxWidth(),
                    style = TextStyle(
                        textDirection = TextDirection.Rtl
                    )
                )
            }
        }
    }
}
