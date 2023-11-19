package qu.lingosnacks.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavDestination(val route: String, val title : String, val icon : ImageVector){
     //Login Screens
    object LoginScreen : NavDestination("login" , "Login" , Icons.Default.ExitToApp) //Icon for top bar
    object SignupScreen : NavDestination("signup" , "Signup" , Icons.Default.AccountCircle) //This icons wont be used

    //Main Screens
    object PackagesScreen : NavDestination("packages" , "Explore" , Icons.Default.AutoStories)
    object MyPackagesScreen : NavDestination("myPackages" , "My Packages" , Icons.Default.FavoriteBorder)

    //Editor Screens                                                               //Just to keep "My Packages" selected
    object PackageEditor : NavDestination("addPackage?packageId={packageId}" , "Package Editor" , Icons.Default.FavoriteBorder)
}