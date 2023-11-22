package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.theme.LightGreyLS
import qu.lingosnacks.viewmodel.PackageViewModel

@Composable
fun PackageList(
    packageViewModel: PackageViewModel,
    packages: List<LearningPackage>,
    currentUser: User,
    mode: String = "explore",
    onReviewPackage: (String) -> Unit, onEditPackage: (String) -> Unit,
    onDeletePackage: (String) -> Unit,
    onDownloadPackage: (String) -> Unit,
    onPackageDetails: (String) -> Unit,
    onFlashCards: (String) -> Unit,
    onUnscrambleGame: (String) -> Unit,
    onMatchGame: (String) -> Unit
) {
    if (packages.isEmpty())
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                "No packages found",
                color = LightGreyLS,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    else
        Column(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(packages) {
                    val packageAvgRating = packageViewModel.getPackageAvgRating(it.packageId)
                    PackageCard(it, packageAvgRating, currentUser, mode,
                        onReviewPackage, onEditPackage,
                        onDeletePackage,
                        onDownloadPackage,
                        onPackageDetails,
                        onFlashCards,
                        onUnscrambleGame,
                        onMatchGame)
                }
            }
        }
}