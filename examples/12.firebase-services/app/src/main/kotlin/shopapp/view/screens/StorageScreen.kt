package shopapp.view.screens

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import shopapp.viewmodel.StorageViewModel
import shopapp.view.components.StorageToolBar
import shopapp.view.components.displayMessage

@ExperimentalFoundationApi
@Composable
fun StorageScreen() {
    val storageViewModel = viewModel<StorageViewModel>()
    val context = LocalContext.current
    var imageToDownload by remember { mutableStateOf("Firestore-ShoppingList-App.png") }

    val imagePicker =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            displayMessage(context, "Select image Uri: $uri")
            println(">> Debug: $uri")
            uri?.let {
                storageViewModel.uploadImage(it)
            }
        }

    val takePicture =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                storageViewModel.uploadImage(it)
                displayMessage(context, "Picture uploaded successfully", Toast.LENGTH_SHORT)
            }
        }

    Scaffold(
        topBar = {
            StorageToolBar(
                onUploadPhotoFromGallery = { imagePicker.launch("image/*") },
                onTakePicture = { takePicture.launch() },
                onRefresh = { storageViewModel.getImageURLs() }
            )
        },
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(it)
        ) {

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                OutlinedTextField(
                    value = imageToDownload,
                    onValueChange = { imageToDownload = it },
                    label = {
                        Text(
                            text = "Image Name"
                        )
                    },
                    placeholder = { Text(text = "Image Name") },
                    singleLine = true
                )
                // Download a Photo
                IconButton(onClick = {
                    if (imageToDownload.isNotEmpty())
                        storageViewModel.getImageUrl(imageToDownload)
                    else
                        displayMessage( context,"Enter the name of the image of download", Toast.LENGTH_SHORT)
                }) {
                    Icon(Icons.Outlined.CloudDownload, null)
                }
            }

            /*if (viewModel.bitmap != null) {
                Image(
                    painter = BitmapPainter(viewModel.bitmap!!.asImageBitmap()),
                    contentDescription = null,
                    modifier = Modifier.size(128.dp)
                )
            }*/

            if (storageViewModel.imageUrl != null) {
                Image(
                    painter = rememberImagePainter(storageViewModel.imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .border(2.dp, Color.Magenta)
                )
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                item() {
                    Text("Long click an image to delete it")
                }

                items(storageViewModel.imageURLs) { imageUrl ->
                    //val imageUri = "https://firebasestorage.googleapis.com/v0/b/cmp312-fall2020.appspot.com/o/images%2FFirebase-Cloud-Storage.png?alt=media&token=9ee1d523-c459-4e73-b7f4-8ef70ca57d49"
                    Column() {
                        Image(
                            painter = rememberImagePainter(imageUrl),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                                .combinedClickable(
                                    onClick = { },
                                    onLongClick = {
                                        storageViewModel.deleteImage(imageUrl)
                                    }
                                )
                        )
                        Text(text = imageUrl.lastPathSegment ?: "")
                    }
                }
            }
        }
    }
}