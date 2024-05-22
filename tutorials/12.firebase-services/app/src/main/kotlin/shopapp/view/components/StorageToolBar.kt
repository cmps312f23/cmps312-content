package shopapp.view.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageToolBar(
    onUploadPhotoFromGallery: ()-> Unit,
    onTakePicture: ()-> Unit,
    onRefresh: ()-> Unit
) {
    val context = LocalContext.current
    TopAppBar(
        //elevation = 4.dp,
        title = {},
        //background = MaterialTheme.colorScheme.primaryContainer,
        navigationIcon = { },
        actions = {
            // Select a photo from Gallery
            IconButton(onClick = { onUploadPhotoFromGallery() }) {
                Icon(Icons.Outlined.Face, null)
            }
            // Take photo using a Camera
            IconButton(onClick = { onTakePicture() }) {
                Icon(Icons.Outlined.AddCircle, null)
            }
            // Refresh
            IconButton(onClick = { onRefresh() }) {
                Icon(Icons.Outlined.Refresh, null)
            }
        })
}