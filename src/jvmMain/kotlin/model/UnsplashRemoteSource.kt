package model

import SensitiveConstants
import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

import kotlinx.serialization.json.Json

class UnsplashRemoteSource {
    suspend fun requestByKtor(keyword: String): ImageResult {
        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })
            }
        }

        return client.get(
            "https://api.unsplash.com/search/photos?" +
                    "client_id=${SensitiveConstants.unsplashKey}" +
                    "&query=$keyword" +
                    "&page=1" +
                    "&per_page=50" +
                    "&lang=en"
        ).body<ImageResult>()
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

        fun clear() {
            INSTANCE = null
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