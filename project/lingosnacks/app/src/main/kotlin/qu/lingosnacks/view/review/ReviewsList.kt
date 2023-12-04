package qu.lingosnacks.view.review

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.Review
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.games.components.StarBar
import qu.lingosnacks.viewmodel.PackageViewModel

@ExperimentalMaterial3Api
@Composable
fun ReviewsList(packageViewModel: PackageViewModel, learningPackage: LearningPackage,
                currentUser: User, onRatePackage: (String) -> Unit) {
    val reviews = packageViewModel.getReviews(learningPackage.packageId)
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (reviews.isEmpty()) {
            Column(
                modifier = Modifier.align(Alignment.Center).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No reviews found",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().height(250.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(reviews) { review ->
                    RatingCard(review, currentUser, onRatePackage)
                }
            }
        }
    }
}

@Composable
fun RatingCard(review: Review, currentUser: User, onRatePackage: (String) -> Unit) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .then(
            if (review.doneBy == currentUser.email) {
                Modifier.border(
                    2.dp,
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.shapes.medium
                )
                    .clickable {
                        onRatePackage(review.packageId)
                    }
            } else {
                Modifier
            }
        )) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {

                    Image(
                        painter = rememberAsyncImagePainter(model = currentUser.photoUrl),
                        contentDescription = "User pfp",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    )

                    Text(
                        text = review.doneBy,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )
                }

                StarBar(reviewAmount = review.rating)

            }

            if (review.comment != null && !review.comment.isBlank()) {
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
            }

            Box(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.align(Alignment.CenterEnd),
                    text = review.doneOn.toString(),
                    style = MaterialTheme.typography.bodyMedium
                )
            }


        }
    }
}