package qu.lingosnacks.ui.packages

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.repository.PackagesRepository
import qu.lingosnacks.ui.editor.components.PackageList
import qu.lingosnacks.ui.editor.components.SearchBox
import qu.lingosnacks.ui.theme.AppTheme

@Composable
fun PackagesScreen(
    packages: List<LearningPackage>,
    onSearch: (String) -> Unit,
    onDeleteClicked: (LearningPackage) -> Unit,
    navigateTo: (String) -> Unit
) {
    Column(modifier = Modifier) {
        SearchBox(onSearch)
        PackageList(
            packages,
            "explore",
            { navigateTo("addPackage?packageId=${it.packageId}") },
            onDeleteClicked
        )
    }
}

@Preview
@Composable
fun ExplorerScreenPreview() {
    AppTheme {
        Surface {
            PackagesScreen(PackagesRepository.packages, {}, {}, {})
        }
    }
}