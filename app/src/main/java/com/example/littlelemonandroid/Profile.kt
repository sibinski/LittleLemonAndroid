package com.example.littlelemonandroid

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.core.content.edit
import java.util.prefs.Preferences

@Composable
fun Profile(navController: NavHostController) {

    // Header section
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.TopCenter), // Align to the top center
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(150.dp) // Adjust size as needed
        )
    }
        Spacer(modifier = Modifier.height(16.dp))

    }
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences(USER_PROFILE, Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString(FIRST_NAME, "") ?: ""
    val lastName = sharedPreferences.getString(LAST_NAME, "") ?: ""
    val email = sharedPreferences.getString(EMAIL, "") ?: ""
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Your profile: ")
        Text("Your first name: $firstName")
        Text("Your last name: $lastName")
        Text("Your email: $email")
        Button(onClick = {
                sharedPreferences.edit { clear() }

                navController.navigate(Onboarding.route) {
                    popUpTo(Onboarding.route) { inclusive = true }
                }
            }, modifier = Modifier.padding(top = 16.dp)) {
            Text("Log out")
        }
    }
}

@Preview
@Composable
fun ProfilePreview(showBackground: Boolean = true)
{
    val navController = rememberNavController()
    Profile(navController)
}