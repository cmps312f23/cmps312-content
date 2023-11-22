package qu.lingosnacks.view.games.popup

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import qu.lingosnacks.entity.User
import qu.lingosnacks.view.theme.AppTheme

@Composable
fun UserProfilePopup(
    navController: NavHostController,
    user: User,
    onLogout: ()-> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {


            Column(
                modifier = Modifier
//                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .align(Alignment.TopCenter)
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "You are Logged In",
                        style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold,
                    )

                }

                Column(
                    modifier = Modifier
                        .wrapContentSize()
//                        .align(Alignment.Center)
                        .padding(vertical = 12.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painter = rememberAsyncImagePainter(
                        model = user.photoUrl),
                    contentDescription = "Profile Picture",
                    Modifier.size(40.dp)
                            .clip(
                                CircleShape
                            )
                            .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape)
                    )

                    Text(text = "Hello, ${user.firstName} ${user.lastName}")
                    Text(text = "Email: ${user.email}")
                    Text(text = "Role: ${user.role}")
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .align(Alignment.BottomEnd)
                        ,
                    horizontalArrangement = Arrangement.Center,
                ) {

                    Button(
                        modifier = Modifier.fillMaxWidth(0.7f),
                        onClick = {

                            onLogout()

                            onDismiss()

                            // Made it navigate up because when i log out in the review screen it
                            // navigates me to the packages list instead of the details screen
                            navController.navigateUp()
//                            val route = NavDestinations.PackagesList.route
//                            navController.navigate(route) {
//                                popUpTo(route) {
//                                    inclusive = true
//                                }
//                            }

                            onDismiss()
                        }) {
                        Text(text = "Log out")

                    }

                }
            }
        }
    }
}

@Preview
@Composable
fun PopupWindowPreview() {
    AppTheme {
        val navController = rememberNavController()
        UserProfilePopup(navController,User("Abdulla","Al-malki","hey@ya.co","","Teacher"), {},{})
    }
}