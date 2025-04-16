package com.example.littlelemonandroid
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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

@Composable
fun Profile(navController: NavHostController) {
    // Header section
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "App Logo",
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(bottom = 46.dp) // Spacer below logo
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = "Profile information:")
    Spacer(modifier = Modifier.height(16.dp))
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
        Text(text = "User's first name: $firstName")
        Text(text = "User's las name: $lastName")
        Text(text = "User's email: $email")
        Button(onClick = {
                sharedPreferences.edit().clear().apply()

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