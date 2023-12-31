package shopapp.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import shopapp.view.ShoppingItemScreen
import shopapp.view.ShoppingListScreen

/**╗
 * It receives navController to navigate between screens
 */
@Composable
fun AppNavigator(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = NavDestination.ShoppingList.route
    ) {

        /* Define the app Navigation Graph
           = possible routes a user can take through the app
        */
        composable(NavDestination.ShoppingList.route) {
            /* Load the ShoppingListScreen and when add/edit item is clicked
            then navigate to the ShoppingItem screen */
            ShoppingListScreen(
                onAddItem = {
                    navController.navigate(NavDestination.ShoppingItem.route)
                },
                onEditItem = {
                    navController.navigate(NavDestination.ShoppingItem.route)
                }
            )
        }

        composable(NavDestination.ShoppingItem.route) {
            /* Load the ShoppingListScreen and when edit item is clicked
            then navigate to the ShoppingItem screen and pass the select itemId as a parameter */
            ShoppingItemScreen(onNavigateBack = { navController.navigateUp() })
        }
    }
}



// and pass the select itemId as a parameter
/*onEditItem = { itemId ->
    navController.navigate("${Screen.ShoppingItem.route}?itemId=$itemId")
}*/

/*val argumentDefinitions = listOf(
    navArgument("itemId") {
        type = NavType.StringType
        nullable = true
    }
)

val route = "${Screen.ShoppingItem.route}?itemId={itemId}"
// verses route receives the itemId as a parameter
composable(route,
    arguments = argumentDefinitions
) { backStackEntry ->
    // Extract the Nav arguments from the Nav BackStackEntry
    val itemId = backStackEntry.arguments?.getString("itemId")?.toLongOrNull()
    ShoppingItemScreen(shoppingItemId = itemId,
        onNavigateBack = { navController.navigateUp() })
}
}*/
