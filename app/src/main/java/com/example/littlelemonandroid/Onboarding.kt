package com.example.littlelemonandroid

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Onboarding() {
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
            label = { Text("Please insert your first name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below First Name

        // Last Name TextField
        var lastName by remember { mutableStateOf("") }
        TextField(
            value = lastName,
            onValueChange = { newValue -> lastName = newValue },
            label = { Text("Please insert your last name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below Last Name

        // Email TextField
        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { newValue -> email = newValue },
            label = { Text("Please insert your email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp)) // Spacer below Email

        // Password TextField
        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { newPassword -> password = newPassword },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth()
        )

        // Spacer before Register Button
        Spacer(modifier = Modifier.height(24.dp))

        // Register Button
        Button(
            onClick = {
                // Handle button click action here (e.g., registration logic)
                println("Register button clicked!")
                println("First Name: $firstName")
                println("Last Name: $lastName")
                println("Email: $email")
                println("Password: $password")
                // You would likely want to pass these values to a registration function
            }
        ) {
            Text("Register")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding()
}