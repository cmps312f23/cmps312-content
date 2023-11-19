package qu.lingosnacks.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.ui.navigateNewStateTo
import qu.lingosnacks.ui.packages.PackagesScreen
import qu.lingosnacks.ui.auth.LoginScreen
import qu.lingosnacks.ui.packages.MyPackagesScreen
import qu.lingosnacks.ui.editor.PackageEditorScreen
import qu.lingosnacks.ui.auth.SignupScreen

@Composable
fun AppNavigator(
    user: String,
    allPackages: List<LearningPackage>,
    userPackages: List<LearningPackage>,
    onLogin: () -> Unit,
    onSearch: (String) -> Unit,
    onDeleteClicked: (LearningPackage) -> Unit,
    onConfirmClicked: (String, LearningPackage) -> Unit,
    navController: NavHostController,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.LoginScreen.route,
        modifier = Modifier.padding(paddingValues)
    ) {
//        mapping of the route to the screen composable
        composable(route = NavDestination.LoginScreen.route) {
            LoginScreen({ route ->
                onLogin()
                navController.navigateNewStateTo(route)
            })
        }
        composable(route = NavDestination.SignupScreen.route) {
            SignupScreen({ route ->
                onLogin()
                navController.navigateNewStateTo(route)
            })
        }
        composable(route = NavDestination.PackagesScreen.route) {
            PackagesScreen(
                allPackages,
                onSearch,
                onDeleteClicked,
                { route -> navController.navigateNewStateTo(route) })
        }
        composable(route = NavDestination.MyPackagesScreen.route) {
            MyPackagesScreen(
                userPackages,
                onDeleteClicked,
                { route -> navController.navigateNewStateTo(route) })
        }
        composable(
            route = NavDestination.PackageEditor.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "-1" })
        ) {
            PackageEditorScreen(
                onConfirmClicked,
                { route -> navController.navigateNewStateTo(route) },
                it.arguments?.getString("packageId")?.toInt() ?: -1
            )
        }
    }
}