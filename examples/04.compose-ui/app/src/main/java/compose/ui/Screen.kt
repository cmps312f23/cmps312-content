package compose.ui

sealed class Screen(val route: String, val title: String, val icon: Int? = null) {
    object Hello : Screen("hello", "Hello (Compose Basics)", R.drawable.ic_home)
    object ComposeLogo : Screen("compose-logo", "Compose Logo (Card)")

    object ClicksCounter : Screen("clicks-counter", "Clicks Counter (Manage State)")
    object Welcome : Screen("welcome", "Welcome (Manage State)")

    object Styling : Screen("styling", "Styling using Modifiers")
    object Clickable : Screen("clickable", "Clickable text (Modifier.clickable)")
    object Divider: Screen("Divider", "Divider")
}