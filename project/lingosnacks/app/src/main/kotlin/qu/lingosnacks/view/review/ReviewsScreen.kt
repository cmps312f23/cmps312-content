package qu.lingosnacks.view.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import qu.lingosnacks.entity.LearningPackage
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.games.components.StarBar
import qu.lingosnacks.viewmodel.PackageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(packageViewModel: PackageViewModel,
                  learningPackage: LearningPackage,
                  currentUser: User,
                  onRatePackage: (String) -> Unit,
                  onSignIn: () -> Unit
) {
    val isLoggedIn = currentUser.uid.isNotEmpty()
    val packageId = learningPackage.packageId

    val packageReviews = packageViewModel.getReviews(packageId)
    val packageReview = packageViewModel.getPackageAvgRating(packageId)

    Column (modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(
            text = "Reviews",
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = MaterialTheme.typography.headlineSmall.toSpanStyle().fontSize,
            fontWeight = FontWeight.Bold,
        )

        // The star review bar and the number of reviews
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            StarBar(
                reviewAmount = packageReview,
                starSize = 15.dp
            )

            Text(text = "${packageReviews.size} Reviews", color = MaterialTheme.colorScheme.primary)
        }
        // Display add review button
        ReviewButton(learningPackage,isLoggedIn, onRatePackage, onSignIn)
        ReviewsList(packageViewModel, learningPackage, currentUser, onRatePackage)
    }
}


@Composable
fun ReviewButton(learningPackage: LearningPackage, isLoggedIn: Boolean,
                 onRatePackage: (String) -> Unit, onSignIn: () -> Unit,
                 modifier: Modifier = Modifier) {
    Button(
        // Review button
        onClick = {
            // Navigate to add review screen if user is logged in
            if ( isLoggedIn ) {
                onRatePackage(learningPackage.packageId)
            } else {
                // navigate to login if user is not logged in
                onSignIn()
            }
        },
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Row(
            modifier = Modifier.wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                text = "Review Package",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Icon(imageVector = Icons.Default.Stars, contentDescription = "Review Package", Modifier.size(18.dp))
        }
    }
}
