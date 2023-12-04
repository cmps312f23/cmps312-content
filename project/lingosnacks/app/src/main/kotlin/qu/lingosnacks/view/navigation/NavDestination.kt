package qu.lingosnacks.view.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.DeveloperBoard
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.JoinRight
import androidx.compose.material.icons.filled.PlaylistAddCheckCircle
import androidx.compose.material.icons.filled.Reviews
import androidx.compose.material.icons.filled.Scoreboard
import androidx.compose.material.icons.filled.Swipe
import androidx.compose.ui.graphics.vector.ImageVector


sealed class NavDestination(val route: String, val title : String, val icon : ImageVector){
    data object Login : NavDestination("login" , "Login" , Icons.Default.ExitToApp) //Icon for top bar
    data object Signup : NavDestination("signup" , "Signup" , Icons.Default.AccountCircle) //This icons wont be used

    data object Packages : NavDestination("packages" , "Packages" , Icons.Default.AutoStories)
    //object MyPackagesScreen : NavDestination("myPackages" , "My Packages" , Icons.Default.FavoriteBorder)

    data object PackageEditor : NavDestination("packageEditor?packageId={packageId}" , "Package Editor" , Icons.Default.FavoriteBorder)

    data object Score: NavDestination("score","Score", Icons.Default.Scoreboard)
    data object PackageDetails: NavDestination("packageDetails/{packageId}","Package Details", Icons.Default.DeveloperBoard)
    data object PackageReviews: NavDestination("reviews/{packageId}","Package Reviews", Icons.Default.Reviews)
    data object ReviewPackage: NavDestination("review/{packageId}","Review Package", Icons.Default.Reviews)
    data object FlashCards: NavDestination("flashcards/{packageId}","Flash Cards", Icons.Default.Swipe)
    data object UnscrambleGame: NavDestination("unscramble/{packageId}","Unscramble Game", Icons.Default.PlaylistAddCheckCircle)
    data object MatchGame: NavDestination("match/{packageId}","Match Game", Icons.Default.JoinRight)
}