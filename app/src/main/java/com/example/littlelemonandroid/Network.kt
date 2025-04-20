package com.example.littlelemonandroid

import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MenuNetwork(
    @SerialName("menu")
    val menuItems: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
) {
    fun toMenuItemRoom() = MenuItemRoom(
        id = id,
        title = title,
        price = price.toDouble(),
        desc = description,
        image = image,
        category = category
    )

    data class MenuItemRoom(
        @PrimaryKey val id: Int,
        val title: String,
        val price: Double,
        val desc: String,
        val image: String,
        val category: String
    )
}
