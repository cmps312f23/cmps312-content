package qu.lingosnacks.view.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.utils.getCurrentRoute
import qu.lingosnacks.view.games.popup.UserProfilePopup
import qu.lingosnacks.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavBar(navController: NavHostController, authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val currentUser by authViewModel.currentUser.collectAsStateWithLifecycle()
    val isUserLogged = currentUser != null

    var profileClicked by remember { mutableStateOf(false) }
    TopAppBar(
        title = {
            Text(text = "Lingosnacks")
        },
        actions = {
            IconButton(
                onClick = {
                    if (!isUserLogged) {
                        navController.navigate(NavDestination.Login.route)
                    }else{
                        profileClicked = true
                    }
                },
                Modifier
                    .padding(5.dp)
                    .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    .size(35.dp)
            ) {
                if (isUserLogged) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            model = currentUser?.photoUrl),
                        contentDescription = "Profile Picture"
                    )
                } else {
                    Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile")
                }
            }
        },
        navigationIcon = {
            val showBackArrow =
                !(getCurrentRoute(navController) == (NavDestination.Score.route) ||
                        getCurrentRoute(navController) == (NavDestination.Packages.route) ||
                        getCurrentRoute(navController) == (NavDestination.Login.route))

            IconButton(
                onClick = { navController.navigateUp() },
                enabled = showBackArrow
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = if (showBackArrow) MaterialTheme.colorScheme.primary else Color.Transparent
                )
            }
        }
    )

    if (isUserLogged && profileClicked) {
        UserProfilePopup(
            navController = navController,
            user = currentUser!!,
            onLogout = {
                authViewModel.signOut()
                profileClicked = false
                displayMessage(context, "Signout successful \uD83D\uDD13")
            },
            onDismiss = {
                profileClicked = false
            }
        )
    }
}