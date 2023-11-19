package qu.lingosnacks.ui.editor.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.repository.PackagesRepository
import qu.lingosnacks.repository.UserRepository
import qu.lingosnacks.ui.packages.MyPackagesScreen
import qu.lingosnacks.ui.theme.AppTheme
import qu.lingosnacks.ui.theme.LightGreyLS

@Composable
fun PackageList(
    packages: List<LearningPackage>,
    mode: String = "explore",
    onEditClicked: (LearningPackage) -> Unit,
    onDeleteClicked: (LearningPackage) -> Unit
) {
    if (packages.isEmpty())
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                "No Packages Yet",
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
                    PackageCard(it, mode, onEditClicked, onDeleteClicked)
                }
            }
        }
}

@Preview
@Composable
fun PackageListPreview() {
    AppTheme {
        Surface {
            MyPackagesScreen(
                PackagesRepository.getPackagesByAuthor(
                    UserRepository.currentUser?.email ?: "t2@test.com"
                ), {}, {})
        }
    }
}