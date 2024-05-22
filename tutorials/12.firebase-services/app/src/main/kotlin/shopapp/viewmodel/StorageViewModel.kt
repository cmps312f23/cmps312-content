package shopapp.viewmodel

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.UUID

class StorageViewModel(private val appContext: Application) : AndroidViewModel(appContext) {
    private val imagesPath = "images/"
    private val storageRef = Firebase.storage.reference
    private val imagesStorageRef = storageRef.child(imagesPath)

    //var bitmap by mutableStateOf<Bitmap?>(null)
    var imageUrl by mutableStateOf<Uri?>(null)
    val imageURLs = mutableStateListOf<Uri>()
    var errorMessage by mutableStateOf("")

    init {
        getImageURLs()
    }

    // Can download the file as Bitmap
    /*fun downloadImage(filename: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val maxDownloadSize = 5L * 1024 * 1024 // 5 MB
            val bytes = imagesStorageRef.child(filename).getBytes(maxDownloadSize).await()
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
        } catch (e: Exception) {
            errorMessage = e.message ?: "Download Failed"
            println(">> Debug: $errorMessage")
        }
    }*/

     fun getImageUrl(filename: String) = viewModelScope.launch(Dispatchers.IO) {
         try {
             imageUrl = imagesStorageRef.child(filename).downloadUrl.await()
         } catch (e: Exception) {
             errorMessage = e.message ?: "Get image URI failed"
             println(">> Debug: $errorMessage")
         }
    }

    fun getImageURLs() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val images = imagesStorageRef.listAll().await()
            imageUrl = null
            imageURLs.clear()
            for (image in images.items) {
                val url = image.downloadUrl.await()
                imageURLs.add(url)
            }
        } catch (e: Exception) {
            errorMessage = e.message ?: "Get images failed"
            println(">> Debug: $errorMessage")
        }
    }

    fun deleteImage(filePath: Uri) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val filename = filePath.lastPathSegment!!
            storageRef.child(filename).delete().await()
            // Remove the image from the list of image URIs
            imageURLs.removeIf { it == filePath }
            println(">> Debug: Image $filename successfully deleted")
        } catch (e: Exception) {
            errorMessage = e.message ?: "Delete failed"
            println(">> Debug: $filePath - $errorMessage")
        }
    }

    fun uploadImage(imageUri: Uri, filename: String = "") =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val filename =
                    filename.ifEmpty { UUID.randomUUID().toString() + ".jpg" }

                imagesStorageRef.child(filename).putFile(imageUri).await()
                val downloadUrl = imagesStorageRef.child(filename).downloadUrl.await()
                imageURLs.add(0, downloadUrl)
                println(">> Debug: Image $filename successfully uploaded")
            } catch (e: Exception) {
                errorMessage = e.message ?: "Upload failed"
                println(">> Debug: $errorMessage")
            }
        }

    fun uploadImage(bitmap: Bitmap, filename: String = "") = viewModelScope.launch(Dispatchers.IO) {
        try {
            // Assign a unique identifier is the filename is empty
            val filename =
                filename.ifEmpty { UUID.randomUUID().toString() + ".jpg" }

            imagesStorageRef.child(filename).putBytes(bitmapToByteArray(bitmap)).await()
            val downloadUrl = imagesStorageRef.child(filename).downloadUrl.await()
            imageURLs.add(0, downloadUrl)
        } catch (e: Exception) {
            errorMessage = e.message ?: "Upload failed"
            println(">> Debug: $errorMessage")
        }
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray
    {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        return stream.toByteArray()
    }

    /*
    fun savePictureToInternalStorage(bitmap: Bitmap, filename: String = ""): Uri? {
        return try {
            // Assign a unique identifier is the filename is empty
            val filename =
                if (filename.isEmpty()) UUID.randomUUID().toString() + ".jpg"
                else filename

            // Save the file to the internal storage
            appContext.openFileOutput(filename, Context.MODE_PRIVATE).use { stream ->
                if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)) {
                    throw IOException("Save bitmap failed.")
                }
            }
            // Get the Uri of the newly save image
            val filePath = appContext.getFileStreamPath(filename)
            return Uri.fromFile(filePath)
        } catch(e: IOException) {
            errorMessage = e.message ?: "Save imaged failed."
            println(">> Debug: $errorMessage")
            null
        }
    }
    */
}