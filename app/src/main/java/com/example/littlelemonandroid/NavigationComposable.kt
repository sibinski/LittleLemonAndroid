package com.example.littlelemonandroid

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavigationComposable(navController: NavHostController) {
    val storedData = storedDataInSharedPreferences()

    NavHost(
        navController = navController,
        startDestination = if (storedData) Home.route else Onboarding.route // Corrected startDestination logic
    ) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            val sampleMenuItems = remember { // Remember the list across recompositions
                listOf(
                    MenuItemNetwork(1, "Hummus", "Chickpea dip", "7.99", "hummus.jpg", "starters"),
                    MenuItemNetwork(2, "Falafel", "Fried chickpea patties", "6.49", "falafel.jpg", "starters")
                    // Add more sample items if needed for preview/testing
                )
            }
            Home(navController = navController, menuItems = sampleMenuItems) // Use the passed navController and provide sample data
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }
    }
}

@Composable
fun storedDataInSharedPreferences(): Boolean {
    val context = LocalContext.current
    val userProfile = null
    val sharedPreferences = context.getSharedPreferences(userProfile, Context.MODE_PRIVATE)
    val userEmail = null
    val email = sharedPreferences.getString(userEmail, "") ?: ""
    return email.isNotBlank()
}