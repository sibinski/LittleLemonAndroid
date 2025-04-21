package com.example.littlelemonandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.littlelemonandroid.ui.theme.LittleLemonAndroidTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {        json(Json {
            ignoreUnknownKeys = true // If your JSON response has extra fields
            isLenient = true        // Allow malformed JSON (if needed, but be cautious)

        })
        }
    }



    private val database by lazy { AppDatabase.getInstance(applicationContext) }



    suspend fun fetchMenu(httpClient: HttpClient): List<MenuItemNetwork> {
        val url =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        return try {
            val response = httpClient.get(url).body<MenuNetwork>()
            response.menuItems
        } catch (e: Exception) {
            // Log the error
            android.util.Log.e("MainActivity", "Error fetching menu: ${e.localizedMessage}", e)
            emptyList() // Or handle the error as appropriate for your app
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadMenuIfNeeded()

        setContent {
            LittleLemonAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )

                {
                    val navController = rememberNavController()
                    NavigationComposable(navController = navController, database = database)
                }
            }
        }
    }




    suspend fun saveMenuToDatabase(menuItemsNetworkList: List<MenuItemNetwork>) {
        val menuItemsRoomList = menuItemsNetworkList.map { networkItem -> MenuItemRoom(
                id = networkItem.id,
                title = networkItem.title,
                desc = networkItem.description,
                price = networkItem.price,
                image = networkItem.image,
                category = networkItem.category
            )
        }
        database.menuItemDao().insertAll(menuItemsRoomList) // Line 78
    }
    fun loadMenuIfNeeded() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                if (database.menuItemDao().countMenuItems() == 0) { // Use countMenuItems()
                    val menuItemsNetworkList = fetchMenu(httpClient)
                    saveMenuToDatabase(menuItemsNetworkList)
                }
            } catch (e: Exception) {
                android.util.Log.e("MainActivity", "Error during loadMenuIfNeeded: ${e.localizedMessage}", e)
                // Handle the error
            }
        }
    }
    }







@Suppress("EXTENSION_SHADOWED_BY_MEMBER")
fun MenuItemNetwork.toMenuItemRoom(): MenuItemRoom {
    return MenuItemRoom(
        id = this.id,
        title = this.title,
        desc = this.description, // Assuming 'description' in Network maps to 'desc' in Room
        price = this.price,
        image = this.image,
        category = this.category
    )
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LittleLemonAndroidTheme {
        Onboarding(navController = rememberNavController())
    }
}