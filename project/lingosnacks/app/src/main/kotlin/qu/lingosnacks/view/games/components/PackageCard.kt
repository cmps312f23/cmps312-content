package qu.lingosnacks.view.games.components

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocalLibrary
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.view.navigation.NavDestination
import qu.lingosnacks.viewmodel.PackageViewModel

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PackageCard(
    packageViewModel: PackageViewModel,
    learningPackage: LearningPackage,
    currentPackage: LearningPackage?,
    navController: NavHostController,
    onCurrentPackageChange: (LearningPackage) -> Unit,
    displayGameScreen: (Boolean) -> Unit = {}
) {

    // Keep track of the package download status
    var isDownloaded by remember { mutableStateOf(learningPackage.isDownloaded) }

    val context = LocalContext.current

    Card(shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                shape = MaterialTheme.shapes.large,
                elevation = 10.dp,
                ambientColor = Color.DarkGray
            )
            .height(150.dp)
            .then(
                // Make the card clickable only if the package is downloaded
                // and display the games screen if the card is clicked
                if (isDownloaded) {
                    Modifier.clickable { displayGameScreen(true) }
                } else {
                    Modifier
                }
            )
            .then(
                // Highlight Card if the package card = the current package in view model
                if (learningPackage.packageId == currentPackage?.packageId) {
                    Modifier.border(
                        2.dp,
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.shapes.large
                    )
                } else {
                    Modifier
                }
            )
            .clickable {
                onCurrentPackageChange(learningPackage)
                navController.navigate(NavDestination.PackageDetails.route)

            }
        ,

    )
    {
        Row {
            // ==================================
           // First is the image to the left start
            // Image will have Rating and package level overlayed on top of it
            Box(modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.large)
                .padding(10.dp))
            {
                // The image of the package
                Image(
                    painter = rememberAsyncImagePainter(learningPackage.iconUrl),
                    contentDescription = "Package Image",
                    modifier = Modifier
                        .fillMaxHeight()
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )

                // the review of the package
                val packageRating = packageViewModel.getPackageAvgRating(learningPackage.packageId)

                // Rating Bar of Stars
                StarBar(reviewAmount = packageRating, modifier = Modifier.align(Alignment.TopEnd))

                // The package level e.g. Beginner, advanced etc.
                PackageLevelComponent(learningPackage, Modifier.align(Alignment.BottomStart))


            }
            // ==================================
            // The Box that contains the package details, download button, and the navigate to package details button
            Box(modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp))
            {
                // Column mainly used to layout the package details at the top and the download button at the bottom using SpaceBetween
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceBetween) {
                    // Next is the package title, language and category
                    Column(
                        modifier = Modifier, verticalArrangement = Arrangement.SpaceEvenly
                    )
                    {
                        Text(
                            text = learningPackage.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            textDecoration = TextDecoration.Underline
                        )
                        Text(
                            text = "Category: ${learningPackage.category}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = learningPackage.language,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium
                        )

                    }

                    // Display download button at the bottom of the card ONLY if the package is downloaded
                        AnimatedVisibility(visible = !isDownloaded) {
                            PackageDownloadButton(
                                onDownloadClick = { learningPackage.isDownloaded = it; isDownloaded = learningPackage.isDownloaded; Toast.makeText( context, "Package Downloaded", Toast.LENGTH_SHORT).show() },
                            )
                        }
                        AnimatedVisibility(visible = isDownloaded) {
                            Row(modifier = Modifier.fillMaxWidth().height(40.dp),
                                horizontalArrangement = Arrangement.SpaceAround) {

                                PackagePlayButton(onPlay = {displayGameScreen(true)})

                                Divider(modifier = Modifier
                                    .fillMaxHeight()
                                    .width(2.dp)
                                    .clip(MaterialTheme.shapes.large),
                                    color =  MaterialTheme.colorScheme.onPrimary,)
                                // Navigate to flash card screen
                                PackageLearnButton(onLearn =  {
                                    // set the this package as the current package
                                    onCurrentPackageChange(learningPackage)
                                    navController.navigate(NavDestination.FlashCards.route)
                                })
                            }
                        }
                }

                // Display the navigate to package details button (disabled this, the whole card is clickable now)
//                PackageNavigateButton(Modifier.align(Alignment.BottomEnd), learningPackage, navController, onCurrentPackageChange)
            }


        }
    }
}

@Composable
fun PackageNavigateButton(
    modifier: Modifier = Modifier,
    learningPackage: LearningPackage,
    navController: NavHostController,
    onCurrentPackageChange: (LearningPackage) -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentSize() // Adjust the size as needed
            .background(MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            onClick = {
                onCurrentPackageChange(learningPackage)
                navController.navigate(NavDestination.PackageDetails.route)
            }) {

            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "View Package Details",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun PackageLevelComponent(learningPackage: LearningPackage, modifier : Modifier = Modifier) {
    var levelColor =
        when (learningPackage.level.lowercase()) {
        "Beginner" -> Color(0xFF8BC34A)
        "Intermediate" -> Color(0xFFFFC107)
        "Advanced" -> Color(0xFFFF5722)
        else -> MaterialTheme.colorScheme.onPrimary // Provide a default color in case none of the options match
    }

    Box(modifier = modifier
        .shadow(shape = MaterialTheme.shapes.large, elevation = 5.dp, ambientColor = Color.DarkGray)
        .background(levelColor, shape = MaterialTheme.shapes.extraSmall)
        .padding(vertical = 5.dp, horizontal = 10.dp)
    ) {
        Text(
            text = learningPackage.level,
            color = Color.DarkGray,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun PackageDownloadButton(onDownloadClick: (Boolean) -> Unit, modifier: Modifier = Modifier) {

        TextButton(
            onClick = {
                onDownloadClick(true)
            }) {
                Text(text = "Download", style = typography.titleSmall)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(imageVector = Icons.Default.Download, contentDescription = "Download Button")
        }
}

@Composable
fun PackagePlayButton(onPlay: ()->Unit){
    TextButton(
        onClick = onPlay
    ) {
        Text(text = "Play", style = typography.titleSmall)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(imageVector = Icons.Default.SportsEsports, contentDescription = "Play Button")
    }
}

@Composable
fun PackageLearnButton(onLearn: ()->Unit){
    TextButton(
        onClick = onLearn
    ) {
        Text(text = "Learn", style = typography.titleSmall)
        Spacer(modifier = Modifier.width(5.dp))
        Icon(imageVector = Icons.Default.LocalLibrary, contentDescription = "Learn Button")
    }
}

//@Preview
//@Composable
//fun PackageCardPrev() {
//    PackageRepo.initPackages(LocalContext.current)
//    Phase1AppTheme {
//        PackageCard(learningPackage = PackageRepo.getPackage(1)!!, navController = rememberNavController(), onCurrentPackageChange = {}, displayGameScreen = {})
//    }
//}