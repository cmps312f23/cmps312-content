package compose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RadioButtonScreen(){
    val languageOptions = listOf("Java", "Kotlin", "JavaScript")
    val ideOptions = listOf("Android Studio", "Visual Studio", "IntelliJ Idea", "Eclipse")

    var selectedLanguage by remember {
        mutableStateOf(languageOptions[1])
    }
    var selectedLanguageIndex by remember {
        mutableStateOf(1)
    }
    var selectedIde by remember {
        mutableStateOf(ideOptions[0])
    }
    var selectedIdeIndex by remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButtonGroup(
            title = "Which is your most favorite language?",
            options = languageOptions,
            selectedOptionIndex = selectedLanguageIndex,
            onOptionSelected = { index, text ->
                run {
                    selectedLanguageIndex = index
                    selectedLanguage = text
                }
            },
            cardBackgroundColor = Color(0xFFFFFAF0)
        )

        RadioButtonGroup(
            title = "Which is your most favorite IDE?",
            options = ideOptions,
            selectedOptionIndex = selectedIdeIndex,
            onOptionSelected = { index, text ->
                run {
                    selectedIdeIndex = index
                    selectedIde = text
                }
            },
            cardBackgroundColor = Color(0xFFF8F8FF)
        )

        val selectedOptions = """
            Selected Language: $selectedLanguage (index: $selectedLanguageIndex)
            Selected IDE: $selectedIde (index: $selectedIdeIndex)
            """.trimIndent()

        Text(
            text = selectedOptions,
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}



@Preview
@Composable
fun RadioButtonScreenPreview() {
    RadioButtonScreen()
}