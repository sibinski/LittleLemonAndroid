package com.example.littlelemonandroid

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController


@Composable
fun Onboarding(navController: NavHostController) {
    val context = LocalContext.current
    val userProfile = null
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header section
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp) // Spacer below logo
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer before instruction text
        Text(text = "Please insert information about yourself next!")
        Spacer(modifier = Modifier.height(16.dp)) // Spacer after instruction text

        // First Name TextField
        var firstName by remember { mutableStateOf("") }
        TextField(
            value = firstName,
            onValueChange = { newValue -> firstName = newValue },
            label = { Text("First name: ") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below First Name

        // Last Name TextField
        var lastName by remember { mutableStateOf("") }
        TextField(
            value = lastName,
            onValueChange = { newValue -> lastName = newValue },
            label = { Text("Last name: ") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below Last Name

        // Email TextField
        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newValue -> email = newValue },
            label = { Text("Email: ") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below Email

        /* Password TextField
        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )


        // Spacer before Register Button
        Spacer(modifier = Modifier.height(24.dp))

        */
        // Register Button
        Button(onClick = {
            if (firstName.isEmpty() && lastName.isEmpty() && email.isEmpty()) {
                Toast.makeText(context,
                    "Registration unsuccessful. Please enter all data.",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val sharedPreferences = context.getSharedPreferences(userProfile, Context.MODE_PRIVATE)
                sharedPreferences.edit(commit = true) { putString("FIRST_NAME", firstName) }
                sharedPreferences.edit(commit = true) { putString("LAST_NAME", lastName) }
                sharedPreferences.edit(commit = true) { putString("EMAIL", email) }

                Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(Home.route)
            }

        }) {
            Text("Register", style = MaterialTheme.typography.labelLarge
            )
        }

    }
}

fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(rememberNavController())
}