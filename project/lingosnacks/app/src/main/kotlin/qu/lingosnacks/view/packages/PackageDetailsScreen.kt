package qu.lingosnacks.view.packages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.User
import qu.lingosnacks.entity.Word
import qu.lingosnacks.view.games.components.PackageLevelComponent
import qu.lingosnacks.view.review.ReviewsScreen
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.viewmodel.PackageViewModel

@ExperimentalMaterial3Api
@Composable
fun PackageDetailsScreen(learningPackage: LearningPackage,
                         currentUser: User,
                         packageViewModel: PackageViewModel,
                         onRatePackage: (String) -> Unit,
                         onSignIn: () -> Unit
) {
    val context = LocalContext.current
    var isDownloaded : Boolean by remember { mutableStateOf( learningPackage.isDownloaded ) }

    fun downloadPackage() {
        isDownloaded = true
        learningPackage.isDownloaded = true
        displayMessage( context, "Package Downloaded")
        packageViewModel.downloadPackage(learningPackage.packageId)
    }

    Scaffold(
        bottomBar = {
            // only show if package is not downloaded
            if (!isDownloaded) {
                // Bottom Bar Button
                Button(
                    onClick = {
                        downloadPackage()
                    },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(horizontal = 10.dp),
                    ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Download Package",
                            fontWeight = FontWeight.Medium,
                            style = MaterialTheme.typography.titleMedium)
                        Icon(imageVector = Icons.Default.Download,
                            modifier = Modifier.padding(horizontal = 10.dp),
                            contentDescription = "Download Package"
                        )
                    }
                }
            }
        }

    ) { paddingValues ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(10.dp)) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally

            ) {
                // Package image Box
                // contains review, title, level, and author name
                Box(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.large)
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp)
                )
                {
                    Image(
                        painter = rememberAsyncImagePainter(model = learningPackage.iconUrl),
                        contentDescription = "Package Image",
                        // makes the image darker
                        colorFilter = ColorFilter.colorMatrix(ColorMatrix().apply {
                            setToScale(
                                0.5f,
                                0.5f,
                                0.5f,
                                1.5f
                            )
                        }),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(140.dp),
                        contentScale = ContentScale.Crop
                    )

                    // Package level, displayed at the top right
                    PackageLevelComponent(learningPackage = learningPackage, Modifier.align(Alignment.TopEnd))

                    // Package title, displayed at the center
                    Text(
                        modifier = Modifier
                            .align(Alignment.Center),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            shadow = Shadow(color = Color.Black, blurRadius = 15f)
                        ),
                        fontWeight = FontWeight.Bold,
                        text = learningPackage.title,
                        color = Color.White,
                        )

                    // Package author, displayed at the bottom left
                    Text(
                        modifier = Modifier.align(Alignment.BottomStart),
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            shadow = Shadow(color = Color.Black, blurRadius = 15f)
                        ),
                        text = "Author: ${learningPackage.author}"
                    )
                }

                WordsList(wordList = learningPackage.words)
                CustomDivider()

                // Package Language and Category at the top of the screen
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)) {
                                append("Language: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(learningPackage.language)
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)) {
                                append("Category: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(learningPackage.category)
                            }
                        },
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }

                // Description
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)) {
                            append("Description: ")
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(learningPackage.description)
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
//                        modifier = Modifier.fillMaxWidth(),
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)) {
                                append("Last Updated: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("${learningPackage.lastUpdatedDate}")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Normal, fontSize = MaterialTheme.typography.bodySmall.fontSize)) {
                                append("Version: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("${learningPackage.version}")
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium
                    )

                }

                CustomDivider()

                ReviewsScreen(packageViewModel, learningPackage,
                    currentUser, onRatePackage, onSignIn)
            }
        }
    }
}

@Composable
fun WordsList(wordList : List<Word>) {
    LazyRow(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        items(wordList) {
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier.height(32.dp)
            ) {
                Text(text = it.text,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun CustomDivider(modifier: Modifier = Modifier) {
    Divider(modifier = modifier.clip(MaterialTheme.shapes.large),
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.primaryContainer
    )
}

/*
@Composable
fun wordsGrid(wordList : List<Word>) {
    LazyHorizontalGrid(rows = GridCells.Fixed(2)) {
        items(wordList) {
            Button(
                onClick = {},
                shape = MaterialTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                Text(
                    text = it.text,
                    fontWeight = FontWeight.Medium,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}*/
