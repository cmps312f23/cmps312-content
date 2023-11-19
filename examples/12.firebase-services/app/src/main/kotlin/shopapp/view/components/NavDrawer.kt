package shopapp.view.components

import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import shop.app.R
import shopapp.view.navigation.NavDestination
import shopapp.viewmodel.AuthViewModel

@Composable
fun NavDrawer(navController: NavController,
              coroutineScope: CoroutineScope,
              drawerState: DrawerState) {
    val authViewModel =
        viewModel<AuthViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)

    val navDrawerItems = mutableListOf<NavDestination>(
        NavDestination.Divider
    )

    //val currentRoute = getCurrentRoute(navController = navController)
    var selectedNavItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))

                /*navItems.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = {
                        Text(text = item.title)
                    },
                    selected = index == selectedNavItemIndex,
                    onClick = {
                        //navController.navigate(item.route)
                        selectedNavItemIndex = index
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = if (index == selectedNavItemIndex) {
                                item.selectedIcon
                            } else item.unselectedIcon,
                            contentDescription = item.title
                        )
                    },
                    badge = {
                        item.badgeCount?.let {
                            Text(text = item.badgeCount.toString())
                        }
                    },
                    modifier = Modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    },*/
                //drawerState = drawerState
                //)
                //Column {
                // Header
                Image(
                    painter = painterResource(id = R.drawable.img_shopping_list_logo),
                    contentDescription = "logo",
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

                authViewModel.currentUser?.let {
                    navDrawerItems.add(0, NavDestination.Logout)
                    Text(
                        text = "Welcome ${it.firstName} ${it.lastName}",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(12.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                } ?: navDrawerItems.add(0, NavDestination.Login)

                // Generate a Row for each navDrawer item
                navDrawerItems.forEachIndexed { index, item ->
                    NavigationDrawerItem(
                        label = {
                            Text(text = item.title)
                        },
                        selected = index == selectedNavItemIndex,
                        //selected = currentRoute == item.route,
                        onClick = {
                            selectedNavItemIndex = index
                            var routeTo = item
                            if (item == NavDestination.Logout) {
                                authViewModel.signOut()
                                routeTo = NavDestination.Login
                            }
                            navController.navigate(routeTo.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                navController.graph.startDestinationRoute?.let { route ->
                                    popUpTo(route)
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                            }
                            // Close drawer
                            coroutineScope.launch {
                                drawerState.close()
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
                //}
            }
        },
        drawerState = drawerState
    ) {}
}

/*
@Composable
fun DrawerItem(item: NavDestination, selected: Boolean, onItemClick: (NavDestination) -> Unit) {
    val background = if (selected)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.primary

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
           Image(
                imageVector = item.icon,
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
}*/

@Preview
@Composable
fun DrawerPreview() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val navController = rememberNavController()
    NavDrawer(navController, coroutineScope, drawerState)
}