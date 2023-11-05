package coroutines.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coroutines.view.components.ClickCounter
import coroutines.view.components.TopBar
import coroutines.viewmodel.RequestState
import coroutines.viewmodel.StockQuotesViewModel

@Composable
fun StockQuotesScreen() {
    val viewModel = viewModel<StockQuotesViewModel>()

    Scaffold(
        topBar = { TopBar("Sequential vs. Parallel Coroutines") }
    ) {
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            OutlinedTextField(
                value = viewModel.selectedCompanies,
                onValueChange = { viewModel.selectedCompanies = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Companies") }
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Execute in Parallel"
                )
                Switch(
                    checked = viewModel.runJobsInParallel,
                    onCheckedChange = { viewModel.runJobsInParallel = it }
                )
                Button(
                    modifier = Modifier.weight(1F),
                    onClick = {
                        viewModel.getStockQuotes()
                    }
                ) {
                    Text(text = "Get Stock Prices")
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(viewModel.companyStockQuotes) { stockQuote ->
                    if (stockQuote.toString().isNotEmpty())
                        Text(text = stockQuote.toString())
                }
            }

            when (viewModel.requestState) {
                RequestState.RUNNING -> {
                    CircularProgressIndicator()
                }

                RequestState.SUCCESS -> {
                    if (viewModel.executionDuration > 0) {
                        Text(
                            text = "Total execution time: ${viewModel.executionDuration}s",
                            color = Color.Blue
                        )
                    }
                    if (viewModel.errorMessage.isNotEmpty()) {
                        Text(text = viewModel.errorMessage, color = Color.Red)
                    }
                }

                RequestState.CANCELLED -> {
                    Text(text = viewModel.errorMessage, color = Color.Red)
                }
            }

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.weight(1F)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.padding(start = 8.dp, end = 8.dp)
                ) {
                    ClickCounter(modifier = Modifier.weight(1F))
                    Text(
                        text = "Click to check that the UI is still responsive \uD83D\uDE03",
                        modifier = Modifier.weight(1F)
                    )
                }
            }
        }
    }
}