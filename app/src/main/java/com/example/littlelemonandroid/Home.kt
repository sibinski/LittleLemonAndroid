
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.example.littlelemonandroid.MenuItemNetwork.MenuItemRoom
import com.example.littlelemonandroid.MenuItemNetwork
import com.example.littlelemonandroid.Profile
import com.example.littlelemonandroid.R
import java.text.NumberFormat


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
fun Home(navController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Profile section (Right upper corner)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd), // Align to the top and end (right)
            horizontalArrangement = Arrangement.End, // Ensure content is at the end
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(48.dp) // Adjust size as needed
                    .clickable {
                        navController.navigate(Profile.route) // Navigate to the Profile screen
                    }
            )
        }

        // Logo section (Top Center)
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

            Spacer(modifier = Modifier.height(16.dp))

        }


        // Welcome Text (Middle of the screen)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center), // Align the Box to the center
            horizontalAlignment = Alignment.Start, // Center the content
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.heroimage),
                contentDescription = "Hero Image",
                modifier = Modifier
                    .fillMaxHeight(0.30F)
                    .align(Alignment.End)
                    .clip(RoundedCornerShape(10.dp))
            )
            Text(text = "Little Lemon")
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Chicago")

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
            )
            MenuItems(
                menuItem = TODO()
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}
@Preview(showBackground = true)
@Composable
fun HomePreview() {
    Home(rememberNavController())
}