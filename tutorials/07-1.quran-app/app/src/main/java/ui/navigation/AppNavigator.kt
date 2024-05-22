package ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import compose.nav.ui.screens.StatsScreen
import ui.common.displayMessage
import ui.components.SurahList
import ui.components.VersesList
import ui.viewmodel.SurahViewModel

/**
 * It receives navcontroller to navigate between screens,
 * padding values -> Since BottomNavigation has some heights,
 * to avoid clipping of screen, we set padding provided by scaffold
 */
@Composable
fun AppNavigator(
    navController: NavHostController
    //padding: PaddingValues
) {
    val surahViewModel = viewModel<SurahViewModel>()
    displayMessage(LocalContext.current, "Surahs count: ${surahViewModel.surahs.size}")
    NavHost(
        navController = navController,
        //set the start destination as home
        startDestination = NavDestination.Surahs.route,
        //Set the padding provided by scaffold
        //modifier = Modifier.padding(paddingValues = padding)) {
    ) {
        /* Define the app Navigation Graph
           = possible routes a user can take through the app
            "quran" -> SurahScreen
            "settings" -> SettingsScreen
            "search" -> SearchScreen
        */
        composable(NavDestination.Surahs.route) {
            /* Load the SurahScreen and when a surah is click then navigate to the verses
            screen and pass the select surahId as a parameter */
            // verses/2
            SurahList(surahViewModel, onSelectSurah = { surahId ->
                navController.navigate("${NavDestination.Verses.route}/$surahId") {
                    popUpTo("settings")
                }
            })
        }

        // verses route receives the surahId as a parameter
        // verses/2
        composable("verses/{surahId}",
            arguments = listOf(navArgument("surahId") { type = NavType.IntType })
        ) { backStackEntry ->
            // Extract the Nav arguments from the Nav BackStackEntry
            backStackEntry.arguments?.getInt("surahId")?.let { surahId ->
                val surah = surahViewModel.getSurah(surahId)
                /* Load the VersesScreen and when the user clicks the back arrow then navigate up */
                VersesList(surah,
                    onNavigateBack = { navController.navigateUp() })
            }
        }

        composable(NavDestination.Stats.route) {
            StatsScreen(surahViewModel)
        }
    }
}