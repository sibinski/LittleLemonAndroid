package com.example.littlelemonandroid

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable


@Composable
fun NavigationComposable(navController: NavHostController) {
    val storedData = storedDataInSharedPreferences()

    NavHost(
        navController = navController,
        startDestination = if (storedData) Onboarding.route else Home.route
    ) {
        composable(Onboarding.route) { Onboarding(navController = navController) }
        composable(Home.route) { Home(navController = navController) }
        composable(Profile.route) { Profile(navController = navController) }
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