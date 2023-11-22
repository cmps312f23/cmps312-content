package qu.lingosnacks.utils

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.todayIn
import java.util.UUID

fun displayMessage(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(context, message, duration).show()
}

@Composable
fun displayMessage(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(LocalContext.current, message, duration).show()
}

@Composable
fun getCurrentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun getCurrentScreen(navController: NavHostController): NavDestination? {
    val navBackStackEntry = navController.currentBackStackEntry
    return navBackStackEntry?.destination
}

fun getPreviousScreen(navController: NavHostController): NavDestination? {
    val navBackStackEntry = navController.previousBackStackEntry
    return navBackStackEntry?.destination
}

fun getPreviousScreenRoute(navController: NavHostController): String? {
    val navBackStackEntry = navController.previousBackStackEntry
    return navBackStackEntry?.destination?.route
}

fun NavHostController.navigateTo(route: String) {
    println("NavHostController.navigateTo: $route")
    this.navigate(route) {
        /*popUpTo(
            this@navigateTo.graph.findStartDestination().id
        )*/
        launchSingleTop = true
    }
}

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun getRandomId() = UUID.randomUUID().toString().split("-")[0]

fun todayDate() : String {
    val today = Clock.System.todayIn(TimeZone.currentSystemDefault())
    return "${today.dayOfMonth}/${today.monthNumber}/${today.year}"
}

fun AnnotatedString.Builder.appendLink(linkText: String, linkUrl: String) {
    pushStringAnnotation(tag = linkUrl, annotation = linkUrl)
    append(linkText)
    pop()
}