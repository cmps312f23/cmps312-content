package compose.nav.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.viewmodel.SurahViewModel

@Composable
fun StatsScreen(viewModel: SurahViewModel) {
    val content = """
                |Surah Count: ${viewModel.surahCount}
                |Aya Count: ${viewModel.ayaCount}
                """.trimMargin()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = "Stats",
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        Text(text = content)
    }
}