package com.example.littlelemonandroid

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemonandroid.Home
import com.example.littlelemonandroid.Onboarding
import com.example.littlelemonandroid.Profile

@Composable
fun NavigationComposable(navController: NavHostController, database: AppDatabase) { // Use the top-level AppDatabase
    val storedData = storedDataInSharedPreferences()
    val menuItems: State<List<MenuItemRoom>> = database.menuItemDao().getAllMenuItems().collectAsState(initial = emptyList())

    NavHost(
        navController = navController,
        startDestination = if (storedData) Home.route else Onboarding.route
    ) {
        composable(Onboarding.route) {
            Onboarding(navController = navController)
        }
        composable(Home.route) {
            Home(navController = navController, menuItems = menuItems.value)
        }
        composable(Profile.route) {
            Profile(navController = navController)
        }
    }
}

@Composable
fun storedDataInSharedPreferences(): Boolean {
    val context = LocalContext.current
    val userProfile = "user_profile_data"
    val sharedPreferences = context.getSharedPreferences(userProfile, Context.MODE_PRIVATE)
    val userEmail = "EMAIL"
    val email = sharedPreferences.getString(userEmail, "") ?: ""
    return email.isNotBlank()
}