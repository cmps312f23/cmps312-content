package compose.ui

import ui.compose.R

sealed class Screen(var route: String, var title: String, var icon: Int? = null) {
    object TextField: Screen("textfield", "TextField")
    object Button : Screen("button", "Button")
    object RadioButton : Screen("radiobutton", "Radio Button")
    object CheckBox : Screen("checkbox", "CheckBox")
    object Switch : Screen("switch", "Switch")
    object Dropdown : Screen("dropdown", "Dropdown")
    object Slider : Screen("slider", "Slider")

    object Card : Screen("card", "Artist Card")
    object Box : Screen("box", "Box Layout")
    object Responsive : Screen("responsive", "Responsive Design")
    object TipCalculator : Screen("TipCalculator", "Tip Calculator", R.drawable.ic_quran)
    object Divider: Screen("Divider", "Divider")
}