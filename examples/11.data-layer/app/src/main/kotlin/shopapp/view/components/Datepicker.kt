package shopapp.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDate
import shopapp.datasource.DateConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Datepicker(dateLabel: String, initialDate: LocalDate,
               onDateSelected: (LocalDate) -> Unit) {

    val dateConverter = DateConverter()
    // First you need to remember a datePickerState
    // This state is where you get the user selection from
    val datePickerState =
        rememberDatePickerState(initialSelectedDateMillis = dateConverter.fromDate(initialDate))

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    var selectedDate by remember {
        mutableStateOf(initialDate)
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDatePicker = false
                    datePickerState.selectedDateMillis?.let {
                        selectedDate = dateConverter.toDate(it)
                    }
                    onDateSelected(selectedDate)
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDatePicker = false
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState,
                title = { Text(text = dateLabel,
                    modifier = Modifier.padding(start = 24.dp)) }
            )
        }
    }

    Button(
        onClick = {
            showDatePicker = true
        }
    ) {
        Text(text = dateLabel)
    }

    Text(
        text = "Selected date: ${selectedDate.dayOfMonth}/${selectedDate.monthNumber}/${selectedDate.year}"
    )
}