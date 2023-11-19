package qu.lingosnacks.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import qu.lingosnacks.R

fun NavHostController.navigateNewStateTo(route: String) {
    println("navigateNewStateTo: $route")
    this.navigate(route) {
        popUpTo(
            this@navigateNewStateTo.graph.findStartDestination().id
        )
        launchSingleTop = true
    }
}

@Composable
fun BottomBar(navController: NavHostController, currentRoute: String?) {
    val bottomNavDestinations = listOf(
        NavDestination.PackagesScreen,
        NavDestination.MyPackagesScreen
    )

    BottomAppBar(containerColor = Color.White,
        //add faint shadow
        modifier = Modifier.shadow(20.dp),
        actions = {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //In our current case, this will always be true but we might want to keep it dynamic for future use
                val isNoOfDesEven = bottomNavDestinations.size % 2 == 0
                val elementAfterMiddle = bottomNavDestinations.size / 2
                bottomNavDestinations.forEachIndexed { index, it ->
                    if (isNoOfDesEven && index == elementAfterMiddle)
                        FloatingActionButton(
                            onClick = { navController.navigateNewStateTo(NavDestination.PackageEditor.route) },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.new_window),
                                contentDescription = "Add Packages"
                            )
                        }
                    val color =
                        if (currentRoute == it.route) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface
                    Column(modifier = Modifier
                        .clickable {
                            navController.navigateNewStateTo(it.route)
                        }
                        .padding(10.dp)
                        .fillMaxHeight()
                        .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (it.route != NavDestination.PackagesScreen.route)
                            Icon(
                                imageVector = it.icon,
                                contentDescription = it.title,
                                tint = color,
                                modifier = Modifier.padding(5.dp)
                            )
                        else
                            Icon(
                                painter = painterResource(id = R.drawable.packages),
                                contentDescription = "My Packages",
                                tint = color,
                                modifier = Modifier.padding(5.dp)
                            )
                        Text(
                            text = it.title,
                            style = MaterialTheme.typography.bodySmall,
                            color = color
                        )
                    }
                }
                if (!isNoOfDesEven)
                    FloatingActionButton(
                        onClick = { navController.navigateNewStateTo(NavDestination.PackageEditor.route) },
                        containerColor = MaterialTheme.colorScheme.secondary
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Add Packages"
                        )
                    }
            }
        })
}