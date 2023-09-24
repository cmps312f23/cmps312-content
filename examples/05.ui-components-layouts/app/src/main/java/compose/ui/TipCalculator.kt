package compose.ui
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import compose.ui.components.RadioButtonGroup
import java.text.NumberFormat

data class TipOption (var tipOption: String, var tipPercentage: Int)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TipCalculator(){
    val tipOptions = listOf(
        TipOption("Okay (10%)", 10),
        TipOption("Good (15%)", 15),
        TipOption("Amazing (20%)", 20)
    )
    var selectedTipOption by remember {
        mutableStateOf(1)
    }
    
    var amount by remember {
        mutableStateOf("")
    }

    var roundUpTip by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text(text = "Bill Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        RadioButtonGroup(
            title = "How was the service?",
            options = tipOptions.map { it.tipOption },
            selectedOptionIndex = selectedTipOption,
            onOptionSelected = { index, _ ->  selectedTipOption = index },
            cardBackgroundColor = Color(0xFFFFFAF0)
        )

        Row {
            Text(
                text = "Round up tip?"
            )
            Switch(
                checked = roundUpTip,
                onCheckedChange = { roundUpTip = it }
            )
        }

        // Format the tip amount according to the local currency e.g., $10.00".
        val tip = calculateTip(amount, tipOptions[selectedTipOption].tipPercentage, roundUpTip)
        if(tip > 0) {
            val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
            Log.d("TipCalculator ", "Tip Amount: $formattedTip")
            Text(
                text = "Tip Amount: $formattedTip",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun calculateTip(amountText: String, tipPercentage: Int, roundUp: Boolean = false): Double {
    val amount = amountText.toDoubleOrNull()

    // If the cost is null or 0, then display 0 tip and exit this function early.
    if (amount == null || amount == 0.0) {
        return 0.0
    }

    // Calculate the tip
    var tip = amount * tipPercentage / 100

    // If the switch for rounding up the tip toggled on (isChecked is true), then round up the
    // tip. Otherwise do not change the tip value.
    if (roundUp) {
        // Take the ceiling of the current tip, which rounds up to the next integer
        tip = kotlin.math.ceil(tip)
    }
    return tip
}

@Preview
@Composable
fun TipCalculatorPreview() {
    TipCalculator()
}