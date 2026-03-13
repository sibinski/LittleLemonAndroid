package com.example.littlelemonandroid

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import java.text.NumberFormat
import androidx.compose.foundation.lazy.items



@Composable
fun GlideImage(model: String, contentDescription: String, modifier: Modifier) {
    val context = LocalContext.current
    var imageState by remember { mutableStateOf<Painter?>(null) }

    DisposableEffect(model) {
        val glide = Glide.with(context)
        var target: Target<*>? = null

        glide.asBitmap()
            .load(model)
            .into(object :
                CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    imageState = resource.asImageBitmap() as Painter?
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    imageState = placeholder?.toBitmap()?.asImageBitmap() as Painter?
                }

                override fun onLoadStarted(placeholder: Drawable?) {
                    imageState = placeholder?.toBitmap()?.asImageBitmap() as Painter?
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    imageState = errorDrawable?.toBitmap()?.asImageBitmap() as Painter?
                }
            })
            .also { target = it }

        onDispose {
            glide.clear(target)
        }
    }

    val painter = imageState ?: painterResource(id = android.R.drawable.ic_menu_report_image)

    Image(
        painter = painter,
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = ContentScale.Crop
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItems(menuItem: MenuItemRoom) {
    Column(modifier = Modifier.padding(16.dp)) {
        GlideImage(
            model = menuItem.image,
            contentDescription = menuItem.title,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = menuItem.title)
        Text(text = NumberFormat.getCurrencyInstance().format(menuItem.price))
        Text(text = menuItem.desc)

    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Home(navController: NavHostController, menuItems: List<com.example.littlelemonandroid.MenuItemRoom>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile section (Right upper corner)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp)
                    .clickable { navController.navigate(Profile.route) }
            )
        }

        // Logo section (Top Center)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp) // Adjust top padding to be below logo
        ) {
            // Welcome and Hero Section
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Little Lemon")
                Text(text = "Chicago")
                Image(
                    painter = painterResource(id = R.drawable.heroimage),
                    contentDescription = "Hero Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Menu") // Add a title for the menu
            }

            // Menu Items Display
            LazyColumn {
                items(menuItems) { item ->
                    MenuItems(menuItem = item)
                }
            }
        }
    }
}