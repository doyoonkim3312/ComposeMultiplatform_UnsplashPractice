package model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ImageResult(
    val results: List<Result>?,
    val total: Int,
    val total_pages: Int
) {
    @Serializable
    data class Result(
        val created_at: String?,
        val current_user_collections: List<@Contextual Any>?,
        val description: String?,
        val height: Int?,
        val id: String?,
        val liked_by_user: Boolean?,
        val likes: Int?,
        val links: Links?,
        val urls: Urls?,
        val user: User?,
        val width: Int?
    ) {
        @Serializable
        data class Links(
            val download: String?,
            val html: String?,
            val self: String?
        )

        @Serializable
        data class Urls(
            val full: String,
            val raw: String,
            val regular: String,
            val small: String,
            val thumb: String
        )

        @Serializable
        data class User(
            val id: String,
            val instagram_username: String?,
            val last_name: String?,
            val links: Links,
            val name: String,
            val portfolio_url: String?,
            val profile_image: ProfileImage?,
            val twitter_username: String?,
            val username: String
        ) {
            @Serializable
            data class Links(
                val html: String?,
                val likes: String?,
                val photos: String?,
                val self: String?
            )

            @Serializable
            data class ProfileImage(
                val large: String?,
                val medium: String?,
                val small: String?
            )
        }
    }
}