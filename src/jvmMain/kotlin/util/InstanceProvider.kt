package util

import model.ImageRepository
import model.UnsplashRemoteSource
import viewmodel.SearchViewModel

/**
 * Instance Provider.
 */

// Function for remove all existing references to trigger GC
fun clearInstances() {
    SearchViewModel.clear()
    UnsplashRemoteSource.clear()
    ImageRepository.clear()
}

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