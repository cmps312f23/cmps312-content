package qu.lingosnacks.view.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import com.gowtham.ratingbar.StepSize
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Review
import qu.lingosnacks.entity.User
import qu.lingosnacks.utils.displayMessage
import qu.lingosnacks.utils.todayDate
import qu.lingosnacks.viewmodel.PackageViewModel

@ExperimentalMaterial3Api
@Composable
fun ReviewEditor(learningPackage: LearningPackage,
                 currentUser: User,
                 packageViewModel: PackageViewModel,
                 onNavigateBack: () -> Unit) {

    val review = packageViewModel.getReview(packageId = learningPackage.packageId, currentUser.email)
        ?: Review()

    var reviewValue by remember { mutableDoubleStateOf( review.rating) }
    var comment by remember { mutableStateOf( review.comment) }
    val context = LocalContext.current

    fun addReview() {
        val reviewObj = review.copy(
            packageId = learningPackage.packageId,
            comment = comment,
            doneOn = todayDate(),
            rating = reviewValue,
            doneBy = currentUser.email
        )

        packageViewModel.upsertReview(reviewObj)
        
        if (review.reviewId.isNotEmpty()) {
           displayMessage( context, "Rating updated")
        } else {
            displayMessage( context, "Thank you for your review!")
        }

        onNavigateBack()
    }

    Scaffold (
        bottomBar = {
            Row (modifier = Modifier.wrapContentHeight().fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        addReview()
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(horizontal = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.primary
                    )

                ) {
                    Text(
                        text = "Submit",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(10.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "Review Package",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

            // Package Details
            Card(
                modifier = Modifier
                    .padding(10.dp)
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.shapes.medium
                    )
                    .fillMaxWidth()
                    .fillMaxHeight(0.35f),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                ) {

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        text = learningPackage.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp, bottom = 15.dp, start = 15.dp, end = 15.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Description: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(learningPackage.description)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Author: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(learningPackage.author)
                            }
                        },
                        style = MaterialTheme.typography.bodyMedium,
                    )

                    Text(
                        buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Language: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(learningPackage.language)
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append(" - Category: ")
                            }
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                                append(learningPackage.category)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Slide on the stars to change the review",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(10.dp)
                )

                RatingBar(
                    size = 58.dp,
                    spaceBetween = 3.dp,
                    value = reviewValue.toFloat(),
                    stepSize = StepSize.HALF,
                    style = RatingBarStyle.Fill(
//                3f, // stroke width
//                Color(0xFFFFC107), // filled color
//                Color(MaterialTheme.colorScheme.secondary.toArgb()) // stroke color
                    ),
                    onValueChange = { reviewValue = it.toDouble() },
                    onRatingChanged = {
//                    Log.d("CommentComponent", "onRatingChanged: $it")
                    })
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Medium)) {
                            append("Your Rating: ")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("$reviewValue")
                        }
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append("/5")
                        }
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Share your thoughts about this package:",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )

                OutlinedTextField(
                    shape = MaterialTheme.shapes.medium,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                    ),
                    minLines = 4,
                    maxLines = 4,
                    value = comment,
                    onValueChange = { comment = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}