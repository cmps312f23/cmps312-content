package qu.lingosnacks.view.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import qu.lingosnacks.entity.User
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.view.editor.components.PackageList
import qu.lingosnacks.view.editor.components.SearchBox
import qu.lingosnacks.viewmodel.PackageViewModel

@Composable
fun PackagesScreen(
    packageViewModel: PackageViewModel,
    currentUser: User,
    onReviewPackage: (String) -> Unit, onEditPackage: (String) -> Unit,
    onAddPackage: () -> Unit,
    onPackageDetails: (String) -> Unit,
    onFlashCards: (String) -> Unit,
    onUnscrambleGame: (String) -> Unit,
    onMatchGame: (String) -> Unit
) {
    val context = LocalContext.current
    val packages = packageViewModel.packages //.collectAsStateWithLifecycle(initialValue = emptyList()).value
    //val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle().value

    Scaffold(
        floatingActionButton = {
            if (currentUser.role == "Author") {
                FloatingActionButton(
                    onClick = { onAddPackage() },
                    containerColor = MaterialTheme.colorScheme.secondary,
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Package"
                    )
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) {
        Column(modifier = Modifier.padding(it)) {
            SearchBox(onSearch = { searchText ->
                packageViewModel.getPackages(searchText)
            })
            PackageList(
                packageViewModel,
                packages,
                currentUser,
                mode = "explore",
                onReviewPackage,
                onEditPackage = { packageId -> onEditPackage(packageId) },
                onDeletePackage = { packageId -> packageViewModel.deleteOnlinePackage(packageId) },
                onDownloadPackage = { packageId ->
                    displayMessage( context, "Package Downloaded")
                    packageViewModel.downloadPackage(packageId)
                },
                onPackageDetails,
                onFlashCards,
                onUnscrambleGame,
                onMatchGame
            )
        }
    }
}