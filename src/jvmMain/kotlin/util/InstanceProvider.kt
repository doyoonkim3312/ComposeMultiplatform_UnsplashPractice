package util

import model.ImageRepository
import model.UnsplashRemoteSource
import viewmodel.SearchViewModel

/**
 * Instance Provider.
 */

// ViewModel
fun searchViewModel(): SearchViewModel {
    return SearchViewModel.getInstance()
}


// Model
fun unsplashRemoteSource(): UnsplashRemoteSource {
    return UnsplashRemoteSource.getInstance()
}

fun imageRepository(): ImageRepository {
    return ImageRepository.getInstance()
}