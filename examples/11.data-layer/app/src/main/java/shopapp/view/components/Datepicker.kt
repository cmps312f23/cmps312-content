package shopapp.view.components

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
import kotlinx.datetime.LocalDate
import shopapp.db.DateConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Datepicker(dateLabel: String, initialDate: LocalDate,
               onDateSelected: (LocalDate) -> Unit){

    val dateConverter = DateConverter()
    // First you need to remember a datePickerState
    // This state is where you get the user selection from
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = dateConverter.fromDate(initialDate))

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
                title = { Text(text = dateLabel) }
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

   /* val selectedDate = remember { mutableStateOf( "${initialDate.dayOfMonth}/${initialDate.monthNumber}/${initialDate.year}" ) }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            // Month is 0 based so add 1
            selectedDate.value = "$dayOfMonth/${month+1}/$year"
            onDateSelected( LocalDate(year, month+1, dayOfMonth) )
        },
        initialDate.year, initialDate.monthNumber-1, initialDate.dayOfMonth
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(onClick = {
            datePickerDialog.show()
        }) {
            Text(text = dateLabel)
        }
        if (selectedDate.value.isNotEmpty())
            Text(text = "Selected Date: ${selectedDate.value}")
    }*/
}