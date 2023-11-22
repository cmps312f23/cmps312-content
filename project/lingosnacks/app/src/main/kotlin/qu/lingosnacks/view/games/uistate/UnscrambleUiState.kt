package qu.lingosnacks.view.games.uistate

data class UnscrambleUiState(
    val currentWord: String = " ",
    val wordsCount: Int = 0,
    val currentWordCount: Int = 1,
    val currentSentenceCount: Int = 1,
    val sentencesCount: Int = 0,
    val isSentenceWrong: Boolean = false,
    val isGameOver: Boolean = false,
    val score: Int = 0,
    val isGameLost: Boolean = false,
    val attempts:Int = 5,
    val displayScore:Int = 0
)