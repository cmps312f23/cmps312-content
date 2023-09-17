package compose.ui.modifier.styling

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StylingScreen() {
    Column {
        TextWidthPadding()
        WidthAndHeightModifier()
        FillHeightModifier()
        ClipModifier()
        //RotateModifier()
    }
}

@Composable
fun TextWidthPadding() {
    Text(
        text = "Padding and margin!",
        modifier =
        Modifier
            .padding(16.dp) // Outer padding (margin)
            .background(color = Color.Yellow) //background color
            .border(
                width = 2.dp,
                color = Color.Gray
            ) // Add a border
            .padding(8.dp) // Inner padding
    )
}

//For width you need to use width(value : Dp)
//For height you need to use height(value: Dp)
@Composable
fun WidthAndHeightModifier() {
    Text(
        text = "Width and Height",
        color = Color.White,
        modifier = Modifier
            .padding(10.dp) // Outer padding (margin)
            .background(Color.Blue)
            .width(200.dp)
            .height(50.dp)
            //.size(width = 250.dp, height = 100.dp) //Alternative way
    )
}

/*
.fillMaxHeight()
Default fraction is 1.0 -> it will occupy the entire height.
If you give any a fraction value (e.g., 0.25)
    will occupy 25% of the screen height .
 */
@Composable
fun FillHeightModifier() {
    Text(
        text = "Text with 25% avaiable height",
        color = Color.White,
        modifier = Modifier
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxHeight(0.25f) //75% area fill
    )
}

// Clip with RoundedCornerShape
@Composable
fun ClipModifier() {
    Text(
        text = "Text with Clipped background",
        color = Color.White,
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.Blue)
            .padding(10.dp)
    )
}

/*
Sets the degrees the view is rotated around the center of the composable.
Increasing values result in clockwise rotation.
Negative degrees are used to rotate in the counterclockwise direction.
*/
@Composable
fun RotateModifier() {
    Box(
        Modifier
            .rotate(45f)
            .size(250.dp)
            .background(Color.Red)
    )
}

@Preview
@Composable
fun TextWidthPaddingPreview() {
    TextWidthPadding()
}

@Preview
@Composable
fun WidthAndHeightModifierPreview() {
    WidthAndHeightModifier()
}

@Preview
@Composable
fun FillHeightModifierPreview() {
    FillHeightModifier()
}

@Preview
@Composable
fun ClipModifierPreview() {
    ClipModifier()
}

@Preview
@Composable
fun RotateModifierPreview() {
    RotateModifier()
}