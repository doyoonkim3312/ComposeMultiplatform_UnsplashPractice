package model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.loadImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URL
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class UnsplashRemoteSource {

    suspend fun requestByKtor(keyword: String) = suspendCoroutine<ImageResult> { continuation ->
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            val result: ImageResult = client.get("https://api.unsplash.com/search/photos?" +
                    "client_id=" +
                    "&query=$keyword" +
                    "&page=1" +
                    "&per_page=50" +
                    "&lang=en").body()
            continuation.resume(result)
        }
    }

    // For singleton pattern.
    companion object {
        private var INSTANCE: UnsplashRemoteSource? = null

        fun getInstance(): UnsplashRemoteSource {
            if (INSTANCE == null) {
                INSTANCE = UnsplashRemoteSource()
            }
            return INSTANCE!!
        }
    }
}

// Data class for Image Results.
data class UnsplashImage(
    val id: String = "",
    val user: String = "",
    val imageUrl: String = "",
    val imageBitmap: ImageBitmap? = null,
    val urlSelf: String = "",
)