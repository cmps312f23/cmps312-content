package maps.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindow
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import google.maps.R

@Composable
fun BasicMap() {
    val quPosition = LatLng(25.37727951601785, 51.49117112159729)
    val zoomLevel = 20f // Buildings
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(quPosition, zoomLevel)
    }

    // Set properties using MapProperties which you can use to recompose the map
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(mapType = MapType.HYBRID) //  isMyLocationEnabled = true
        )
    }
    val mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(mapToolbarEnabled = true, zoomControlsEnabled = true)
        )
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        properties = mapProperties, uiSettings = mapUiSettings,
        modifier = Modifier.fillMaxSize()
    ) {
        // A Snippet is a text displayed below the title
        val snippetText = "Lat: ${quPosition.latitude}, Long: ${quPosition.longitude}"
        /*Marker(
            state = MarkerState(position = quLocation),
            title = "Qatar University",
            snippet = snippetText
        )*/

        MarkerInfoWindow(
            state = MarkerState(position = quPosition),
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(35.dp)
                    ),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_qu),
                        contentDescription = "QU",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .height(100.dp)
                            .fillMaxWidth(),
                    )
                    Text(
                        text = "My Uni جامعتي",
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(
                        text = snippetText,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }
    }
}