package qu.lingosnacks.ui.packages

import androidx.compose.runtime.Composable
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.ui.editor.components.PackageList

@Composable
fun MyPackagesScreen(
    packages: List<LearningPackage>,
    onDeleteClicked: (LearningPackage) -> Unit,
    navigateTo: (String) -> Unit
) {
    PackageList(
        packages,
        "my packages",
        { navigateTo("addPackage?packageId=${it.packageId}") },
        onDeleteClicked
    )
}