package com.example.littlelemonandroid

import com.example.littlelemonandroid.MenuNetwork // Adjust the import path if your package structure is different
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.MutableStateFlow

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
        }
    }

    private val menuUrl = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
    private val _menuItems = MutableStateFlow<List<MenuItemNetwork>>(emptyList())
    val menuItems = _menuItems

    @Database(entities = [AppDatabase::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun menuItemDao(): MenuItemDao
    }

    private val database by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "little-lemon")
            .fallbackToDestructiveMigration()
            .build()
    }


    suspend fun fetchMenu(httpClient: HttpClient): List<MenuItemNetwork> {
        val url =
            "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response = httpClient.get(url).body<MenuNetwork>()
        return response.menuItems
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LittleLemonAndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavigationComposable(navController = navController)
                }
            }
        }
    }




        fun saveMenuToDatabase(menuItemsNetworkList: List<MenuItemNetwork>) {
            val menuItemsRoomList = menuItemsNetworkList.map { networkItem ->
                MenuItemRoom(
                    id = networkItem.id,
                    title = networkItem.title,
                    desc = networkItem.description,
                    price = networkItem.price,
                    image = networkItem.image,
                    category = networkItem.category
                )
            }
            database.menuItemDao().insertAll(menuItemsRoomList)

            fun loadMenuIfNeeded() {
                lifecycleScope.launch(Dispatchers.IO) {
                    if (database.menuItemDao().equals(0)) {
                        val menuItemsNetworkList = fetchMenu(httpClient)
                        saveMenuToDatabase(menuItemsNetworkList)
                    }
                }
            }




        }
    }





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