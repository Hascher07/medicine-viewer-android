package com.app.medicineviewer

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.app.medicineviewer.presentation.HomeScreen
import com.app.medicineviewer.presentation.LoginScreen
import com.app.medicineviewer.presentation.medicine.MedicineDetailScreen
import com.app.medicineviewer.presentation.medicine.MedicineViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavGraph(navController: NavHostController, viewModel: MedicineViewModel) {
    AnimatedNavHost(navController = navController, startDestination = "login") {
        composable(route = "login") {
            LoginScreen(onLoginSuccess = { username ->
                navController.navigate("greeting/$username")
            })
        }
        composable(
            route = "greeting/{username}",
            enterTransition = { slideInHorizontally(animationSpec = tween(300)) { it } },
            exitTransition = { slideOutHorizontally(animationSpec = tween(300)) { -it } },
            popEnterTransition = { slideInHorizontally(animationSpec = tween(300)) { -it } },
            popExitTransition = { slideOutHorizontally(animationSpec = tween(300)) { it } }
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username") ?: ""
            HomeScreen(username = username, viewModel = viewModel) { medicine ->
                navController.navigate("medicine_detail/${medicine.id}")
            }
        }
        composable(
            route = "medicine_detail/{medicineId}",
            enterTransition = { fadeIn(animationSpec = tween(800)) },
            exitTransition = { fadeOut(animationSpec = tween(800)) },
            popEnterTransition = { fadeIn(animationSpec = tween(800)) },
            popExitTransition = { fadeOut(animationSpec = tween(800)) }
        ) { backStackEntry ->
            val medicineId = backStackEntry.arguments?.getString("medicineId") ?: ""
            MedicineDetailScreen(medicineId = medicineId, viewModel = viewModel)
        }
    }
}
