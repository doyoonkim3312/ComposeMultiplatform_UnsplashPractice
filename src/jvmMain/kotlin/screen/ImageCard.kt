package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import model.UnsplashImage
import org.jetbrains.skia.Bitmap
import java.awt.Desktop
import java.net.URI
import java.net.URL


@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    data: UnsplashImage = UnsplashImage()
) {
    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.wrapContentSize()
            .padding(20.dp),
        shape = RoundedCornerShape(15.dp)
    ) {
        Column(
            modifier = Modifier.wrapContentSize()
                .background(MaterialTheme.colors.primary),
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.user,
                fontSize = 21.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(top = 5.dp)
            )

            AsyncImage(
                imageUrl = data.imageUrl,
                contentDescription = "Image from Unsplash",
                contentScale = ContentScale.Fit,
                modifier = Modifier.wrapContentSize()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .clickable {
                        openWebpage(URI.create(data.urlSelf))
                    }
            )

        }
    }

}

private fun openWebpage(uri: URI) {
    if (Desktop.isDesktopSupported()) {
        val desktop = Desktop.getDesktop()
        if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                desktop.browse(uri)
            } catch(e: Exception) {
                println("Unable to browse url: ${e.printStackTrace()}")
            }
        }
    } else {
        return
    }
}

// Load Image Asynchronously from remote source.
// Reference: https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Image_And_Icons_Manipulations/README.md#loading-images-from-device-storage-or-network-asynchronously
@Composable
fun AsyncImage(
    imageUrl: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
    modifier: Modifier = Modifier
) {
    var imageBitmap = remember { mutableStateOf<BitmapPainter?>(null) }

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            imageBitmap.value = BitmapPainter(loadImageBitmap(imageUrl))
        }
    }

    Image(
        painter = imageBitmap.value ?: painterResource("placeholder.png"),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )


}
private suspend fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)



@Preview
@Composable
fun ImageCard_Preview() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ImageCard(
            data = UnsplashImage("id1", "user1", "url1", null)
        )
    }
}
