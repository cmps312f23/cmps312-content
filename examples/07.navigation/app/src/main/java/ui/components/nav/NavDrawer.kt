package ui.components.nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import ui.screens.Screen
import ui.screens.getCurrentRoute

@Composable
fun NavDrawer(
    navController: NavController, drawerState: DrawerState,
    drawerContent: @Composable () -> Unit
) { //, coroutineScope: CoroutineScope) { //, scaffoldState: ScaffoldState) {
    val navItems = listOf(
        Screen.Profile,
        Screen.Addresses, Screen.Orders, Screen.Divider,
        Screen.Settings, Screen.Divider, Screen.FAQ
    )

    val currentRoute = getCurrentRoute(navController = navController)
    val coroutineScope = rememberCoroutineScope()
    var selectedNavItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                navItems.forEachIndexed { index, item ->
                    if (item.title == "Divider") {
                        Divider()
                    } else {
                        val itemIconImage =
                            item.icon ?: ImageVector.vectorResource(item.iconResourceId!!)
                        NavigationDrawerItem(
                            label = {
                                Text(text = item.title)
                            },
                            icon = {
                                Icon(
                                    imageVector = itemIconImage,
                                    contentDescription = item.title
                                )
                            },
                            selected = index == selectedNavItemIndex,
                            onClick = {
                                navController.navigate(item.route)
                                selectedNavItemIndex = index
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                            },
                            modifier = Modifier
                                .padding(NavigationDrawerItemDefaults.ItemPadding)
                        )
                    }
                }
            }
        },
        drawerState = drawerState
    )
    {
        drawerContent()
    }

    /*ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
        // Header
        Image(
            painter = painterResource(id = R.drawable.img_logo),
            contentDescription = R.drawable.img_logo.toString(),
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )

        // Generate a Row for each navDrawer item
        navDrawerItems.forEach { item ->
            NavigationDrawerItem(item = item, selected = currentRoute == item.route, onItemClick = {
                navController.navigate(item.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route)
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                }
                // Close drawer
                coroutineScope.launch {
                    //scaffoldState.drawerState.close()
                }
            })
        }
        // Fill the remaining space so as to have the text at the bottom of the nav drawer
        Spacer(modifier = Modifier.weight(1F))
        Text(
            text = "Developed by CMPS 312 Team",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}*/
}


@Composable
fun DrawerItem(item: Screen, selected: Boolean, onItemClick: (Screen) -> Unit) {
    val background = if (selected)
        MaterialTheme.colorScheme.primary
    else
        MaterialTheme.colorScheme.primaryContainer

    if (item.title == "Divider") {
        Divider()
    } else {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onItemClick(item) })
                .height(45.dp)
                .background(background)
                .padding(start = 10.dp)
        ) {
            // For each screen either an icon or vector resource is provided
            val icon = item.icon ?: ImageVector.vectorResource(item.iconResourceId!!)
            Image(
                imageVector = icon,
                contentDescription = item.title,
                colorFilter = ColorFilter.tint(Color.White),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
            Spacer(modifier = Modifier.width(7.dp))
            Text(
                text = item.title,
                fontSize = 18.sp,
                color = Color.White
            )
        }
    }
}

@Preview
@Composable
fun DrawerPreview() {
    //val coroutineScope = rememberCoroutineScope()
    //val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    NavDrawer(navController = navController, drawerState) {
        Text("Test")
    }
}