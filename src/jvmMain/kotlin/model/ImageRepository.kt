package model

import kotlinx.coroutines.flow.flow
import util.unsplashRemoteSource

class ImageRepository(
    private val remoteSource: UnsplashRemoteSource = unsplashRemoteSource()
) {

    fun fetchImageByKtor(keyword: String) = flow<Result<UnsplashImage>> {
        remoteSource.requestByKtor(keyword).results?.forEach { received ->
            if (received != null) {
                emit(
                    Result.success(
                        UnsplashImage(
                            id = received.id!!,
                            user = received.user!!.name,
                            imageUrl = received.urls!!.regular,
                            urlSelf = received.links!!.html!!
                        )
                    )
                )
            } else {
                emit(Result.failure(Exception("Unable to process the request.")))
            }
        }
    }

    companion object {
        private var INSTANCE: ImageRepository? = null

        fun getInstance(): ImageRepository {
            if (INSTANCE == null) INSTANCE = ImageRepository()
            return INSTANCE!!
        }
    }
}