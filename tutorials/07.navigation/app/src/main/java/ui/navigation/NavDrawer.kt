package ui.navigation

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
import ui.screens.getCurrentRoute

@Composable
fun NavDrawer(
    navController: NavController, drawerState: DrawerState,
    drawerContent: @Composable () -> Unit
) {
    val navItems = listOf(
        NavDestination.Profile,
        NavDestination.Addresses, NavDestination.Orders, NavDestination.Divider,
        NavDestination.Settings, NavDestination.Divider, NavDestination.FAQ
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
}


@Composable
fun DrawerItem(item: NavDestination, selected: Boolean, onItemClick: (NavDestination) -> Unit) {
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