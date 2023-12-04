package qu.lingosnacks.view.games.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.gowtham.ratingbar.RatingBar
import com.gowtham.ratingbar.RatingBarStyle
import qu.lingosnacks.view.theme.AppTheme


@Composable
fun StarBar(
    modifier: Modifier = Modifier,
    reviewAmount : Double,
    starSize: Dp = 10.dp
//    starCount: Int = 5,
//    review: Float = 0f,
    ) {

    ElevatedCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
        Row( modifier = modifier.padding(5.dp)) {
            RatingBar(
                value = reviewAmount.toFloat(),
                size = starSize,
                spaceBetween = 0.dp,
                style = RatingBarStyle.Fill(
//                3f, // strok wiidth
                    activeColor =  Color(0xFFFFC107), // filled color
                    inActiveColor = Color(MaterialTheme.colorScheme.onPrimaryContainer.toArgb())// stroke color
                ), // unfilled color

                onValueChange = {}, onRatingChanged = {})
        }
    }

}

@Preview
@Composable
fun StarsRatingCardPrev() {
    AppTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            StarBar(reviewAmount = 1.5)
        }
    }
}

// Unused -----------

//@Composable
//fun RatingStarsCard(reviewAmount: Double, starIconColor : Color = MaterialTheme.colorScheme.primary, modifier: Modifier = Modifier) {
//    // Rating
//    return Row(
//        horizontalArrangement = Arrangement.spacedBy(5.dp),
//        verticalAlignment = Alignment.CenterVertically,
//        modifier = modifier
//            .background(
//                MaterialTheme.colorScheme.onPrimary,
//                shape = MaterialTheme.shapes.small
//            )
//            .padding(horizontal = 4.dp, vertical = 3.dp)
//            .wrapContentSize()
//    ) {
//        // Star Icon
//        Icon(imageVector = Icons.Default.Star,
//            tint = starIconColor,
//            contentDescription = "Rating Stars",
//            modifier = Modifier.size(18.dp),)
//        // Rating in decimal
//        Text(
//            buildAnnotatedString {
//                withStyle(
//                    style = SpanStyle(
//                        fontWeight = FontWeight.Medium,
//                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                ) {
//                    append("${String.format("%.1f",reviewAmount)}")
//                }
//                withStyle(
//                    style = SpanStyle(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
//                        color = MaterialTheme.colorScheme.primary
//                    )
//                ) {
//                    append(" /5")
//
//                }
//            },
//            style = MaterialTheme.typography.titleMedium
//        )
//
//    }
//}

//@Composable
//fun TotalRatingCard( reviewTitle : String ,learningPackage: LearningPackage, modifier: Modifier) {
//
//    Row (modifier = modifier,
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.spacedBy(10.dp)) {
//        Text(text = reviewTitle,
//            color = MaterialTheme.colorScheme.primary,
//            fontWeight = FontWeight.Medium,
//            style = MaterialTheme.typography.titleMedium)
//        val reviewAmount = PackageRepo.getAveragePackageRating(learningPackage.packageId)
//        RatingStarsCard(reviewAmount, modifier = modifier)
//    }
//
//}
