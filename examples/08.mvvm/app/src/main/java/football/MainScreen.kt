package football

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import compose.nav.football.view.navigation.AppNavigator
import compose.nav.football.view.navigation.BottomNavBar
import football.view.common.displayMessage
import football.viewmodel.UserViewModel

@Composable
fun MainScreen() {
    //remember navController so it does not get recreated on recomposition
    val userViewModel = viewModel<UserViewModel>()
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar(userViewModel) },
        bottomBar = { BottomNavBar(navController) }
    ) {
        paddingValues -> AppNavigator(navController, userViewModel,paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(userViewModel: UserViewModel) {
    //val userViewModel = viewModel<UserViewModel>(viewModelStoreOwner = LocalContext.current as ComponentActivity)
    displayMessage(LocalContext.current, "Current user: ${userViewModel.currentUser}")
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "MVVM App")

                if (userViewModel.currentUser.isNotEmpty()) {
                    Text(
                        text = " - Welcome ${userViewModel.currentUser}",
                    )
                }
            }
        }
    )
}