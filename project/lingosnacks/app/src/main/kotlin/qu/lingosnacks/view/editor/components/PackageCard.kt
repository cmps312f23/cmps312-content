package qu.lingosnacks.view.editor.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import qu.lingosnacks.R
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.User
import qu.lingosnacks.entity.isAuthor
import qu.lingosnacks.view.navigation.NavDestination
import qu.lingosnacks.view.theme.BlueLS
import qu.lingosnacks.view.theme.CyanLS
import qu.lingosnacks.view.theme.DarkGreyLS
import qu.lingosnacks.view.theme.LightGreyLS
import qu.lingosnacks.view.theme.OrangeLS
import qu.lingosnacks.view.theme.PurpleLS
import qu.lingosnacks.view.theme.RedLS
import qu.lingosnacks.view.theme.YellowLS

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PackageCard(
    learningPackage: LearningPackage,
    avgPackageRating: Double,
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
    /*val userFullName =
        UserRepository.getUserByEmail(learningPackage.author)?.fullName ?: "No Author"*/
    var openDialog by rememberSaveable { mutableStateOf(false) }

    if (openDialog)
        DeleteDialog(learningPackage, { openDialog = false }, {
        openDialog = false
        onDeletePackage(learningPackage.packageId)
    })

    Card(
        modifier = Modifier.padding(10.dp, 10.dp, 10.dp, 5.dp),
        elevation = CardDefaults.cardElevation(2.5.dp),
        shape = RoundedCornerShape(size = 12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, LightGreyLS)
    ) {
        Row(
            modifier = Modifier.padding(all = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(learningPackage.iconUrl.ifEmpty { R.drawable.logo_square })
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                modifier = Modifier.size(80.dp),
                contentDescription = null
            ) {
                when (painter.state) {
                    is AsyncImagePainter.State.Error -> {}
                    is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                    else -> Image(
                        painter = painter,
                        contentDescription = contentDescription
                    )
                }
            }
            Spacer(modifier = Modifier.width(width = 10.dp)) // gap between image and text
            Column {
                Text(
                    text = learningPackage.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 2.5.dp)
                )
                if (mode == "explore") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Filled.Person,
                            null,
                            tint = DarkGreyLS,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = learningPackage.author,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = DarkGreyLS,
                            modifier = Modifier.padding(
                                start = 2.5.dp,
                                top = 5.dp,
                                bottom = 5.dp
                            )
                        )
                    }
                } else
                    Text(
                        text = "Last updated: ${learningPackage.lastUpdatedDate}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkGreyLS,
                        modifier = Modifier.padding(start = 2.5.dp, top = 5.dp, bottom = 5.dp)
                    )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 2.5.dp, bottom = 10.dp)
                ) {
                    IntRange(1, avgPackageRating.toInt()).forEach {
                        Icon(
                            Icons.Default.Star,
                            null,
                            tint = YellowLS,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    IntRange(avgPackageRating.toInt() + 1, 5).forEach {
                        Icon(
                            Icons.Outlined.StarOutline,
                            null,
                            tint = YellowLS,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Text(
                        text = " (${String.format("%.1f", avgPackageRating)})",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = DarkGreyLS
                    )
                }
                FlowRow(
                    //verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 2.5.dp, top = 5.dp)
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(2.dp, PurpleLS),
                        modifier = Modifier.padding(end = 5.dp)
                    ) {
                        Text(
                            learningPackage.level,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 7.5.dp)
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(2.dp, CyanLS),
                        modifier = Modifier.padding(end = 5.dp)
                    ) {
                        Text(
                            learningPackage.language,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 7.5.dp)
                        )
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        shape = RoundedCornerShape(50),
                        border = BorderStroke(2.dp, OrangeLS)
                    ) {
                        Text(
                            learningPackage.category,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(horizontal = 7.5.dp)
                        )
                    }
                }
            }
        }
        Divider(thickness = 1.dp, color = LightGreyLS)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 5.dp, end = 10.dp, bottom = 5.dp)
        ) {

            IconButton(
                onClick = { onReviewPackage(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    NavDestination.ReviewPackage.icon,
                    contentDescription = NavDestination.ReviewPackage.title,
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            // ToDo: If already downloaded then show sync button instead
            IconButton(
                onClick = { onDownloadPackage(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    Icons.Default.Download,
                    contentDescription = "Download",
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            IconButton(
                onClick = { onFlashCards(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    NavDestination.FlashCards.icon,
                    contentDescription = NavDestination.FlashCards.title,
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            IconButton(
                onClick = { onUnscrambleGame(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    NavDestination.UnscrambleGame.icon,
                    contentDescription = NavDestination.UnscrambleGame.title,
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            IconButton(
                onClick = { onMatchGame(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    NavDestination.MatchGame.icon,
                    contentDescription = NavDestination.MatchGame.title,
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            IconButton(
                onClick = { onPackageDetails(learningPackage.packageId) },
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    NavDestination.PackageDetails.icon,
                    contentDescription = NavDestination.PackageDetails.title,
                    tint = Color(0xFF000000),
                )
            }
            Spacer(modifier = Modifier.width(width = 8.dp))

            if (currentUser.role == "Author" &&
                learningPackage.isAuthor(currentUser)
            ) {
                IconButton(
                    onClick = { onEditPackage(learningPackage.packageId) },
                    modifier = Modifier.size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Package",
                        modifier = Modifier.size(30.dp),
                        tint = BlueLS
                    )
                }
                Spacer(modifier = Modifier.width(width = 8.dp))
                IconButton(onClick = { openDialog = true }, modifier = Modifier.size(30.dp)) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete Button",
                        modifier = Modifier.size(30.dp),
                        tint = RedLS
                    )
                }
            }
        }
    }
}