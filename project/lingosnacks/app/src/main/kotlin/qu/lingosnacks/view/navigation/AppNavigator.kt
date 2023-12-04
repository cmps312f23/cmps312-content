package qu.lingosnacks.view.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import qu.lingosnacks.entity.User
import qu.lingosnacks.utils.navigateTo
import qu.lingosnacks.view.auth.LoginScreen
import qu.lingosnacks.view.auth.SignupScreen
import qu.lingosnacks.view.editor.PackageEditorScreen
import qu.lingosnacks.view.games.screens.FlashCardsScreen
import qu.lingosnacks.view.games.screens.MatchGameScreen
import qu.lingosnacks.view.games.screens.ScoreScreen
import qu.lingosnacks.view.games.unscramble.UnscrambleGameScreen
import qu.lingosnacks.view.packages.PackageDetailsScreen
import qu.lingosnacks.view.packages.PackagesScreen
import qu.lingosnacks.view.review.ReviewEditor
import qu.lingosnacks.view.review.ReviewsList
import qu.lingosnacks.viewmodel.AuthViewModel
import qu.lingosnacks.viewmodel.PackageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavigator(
    authViewModel: AuthViewModel,
    navController: NavHostController,
    paddingValues: PaddingValues
) {
    val packageViewModel = viewModel<PackageViewModel>()

    // Assigning empty user is done for simplification to avoid dealing with null
    val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle().value ?: User("")
    NavHost(
        navController = navController,
        startDestination = NavDestination.Login.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        // Mapping of the route to the screen composable
        composable(route = NavDestination.Login.route) {
            LoginScreen(authViewModel,
                onSignIn = {
                    navController.navigateTo(NavDestination.Packages.route)
                },
                onSignUp = {
                    navController.navigateTo(NavDestination.Signup.route)
                })
        }

        composable(route = NavDestination.Signup.route) {
            SignupScreen(
                authViewModel,
                onSignUp = {
                    navController.navigateTo(NavDestination.Packages.route)
                },
                onSignIn = {
                    navController.navigateTo(NavDestination.Login.route)
                },
            )
        }

        composable(route = NavDestination.Packages.route) {
            PackagesScreen(
                packageViewModel,
                currentUser,
                onReviewPackage = { packageId ->
                    navController.navigateTo(
                        NavDestination.ReviewPackage.route
                            .replace("{packageId}", packageId)
                    )
                },
                onEditPackage = { packageId ->
                    navController.navigateTo(
                        NavDestination.PackageEditor.route
                            .replace("{packageId}", packageId)
                    )
                },
                onAddPackage = {
                    navController.navigateTo(NavDestination.PackageEditor.route)
                },
                onPackageDetails = { packageId ->
                    navController.navigateTo(
                        NavDestination.PackageDetails.route
                            .replace("{packageId}", packageId)
                    )
                },
                onFlashCards = { packageId ->
                    navController.navigateTo(
                        NavDestination.FlashCards.route
                            .replace("{packageId}", packageId)
                    )
                },
                onUnscrambleGame = { packageId ->
                    navController.navigateTo(
                        NavDestination.UnscrambleGame.route
                            .replace("{packageId}", packageId)
                    )
                },
                onMatchGame = { packageId ->
                    navController.navigateTo(
                        NavDestination.MatchGame.route
                            .replace("{packageId}", packageId)
                    )
                }
            )
        }

        composable(
            route = NavDestination.PackageEditor.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            PackageEditorScreen(
                packageId,
                packageViewModel,
                authViewModel,
                onNavigateBack = { navController.popBackStack() } //navController.navigateUp() }
            )
        }

        composable(NavDestination.Score.route) {
            ScoreScreen(packageViewModel, currentUser, onNavigateToPackage = { packageId ->
                navController.navigateTo(
                    NavDestination.PackageDetails.route
                        .replace("{packageId}", packageId)
                )
            })
        }

        composable(
            NavDestination.PackageDetails.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)
            PackageDetailsScreen(learningPackage, currentUser,
                packageViewModel,
                onRatePackage = {
                    navController.navigateTo(
                        NavDestination.ReviewPackage.route
                            .replace("{packageId}", packageId)
                    )
                },
                onSignIn = {
                    navController.navigateTo(NavDestination.Login.route)
                })
        }

        composable(
            NavDestination.PackageReviews.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)
            ReviewsList(packageViewModel, learningPackage, currentUser,
                onRatePackage = {
                    navController.navigateTo(
                        NavDestination.ReviewPackage.route
                            .replace("{packageId}", packageId)
                    )
                })
        }

        composable(NavDestination.ReviewPackage.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)
            ReviewEditor(learningPackage, currentUser, packageViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                })
        }

        composable(NavDestination.UnscrambleGame.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)

            val sentences = learningPackage.words.flatMap { it.sentences }
            UnscrambleGameScreen(sentences, currentUser, packageId, packageViewModel)
            /*UnscrambleGameScreen(learningPackage,
                currentUser,
                packageViewModel,
                unscrambleGameViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                })*/
        }

        composable(NavDestination.MatchGame.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)

            MatchGameScreen(learningPackage, currentUser, packageViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                })
        }

        composable(NavDestination.FlashCards.route,
            arguments = listOf(navArgument("packageId") { defaultValue = "0" })
        ) {
            val packageId = it.arguments?.getString("packageId") ?: "0"
            val learningPackage = packageViewModel.getPackage(packageId)
            FlashCardsScreen(learningPackage)
        }
    }
}