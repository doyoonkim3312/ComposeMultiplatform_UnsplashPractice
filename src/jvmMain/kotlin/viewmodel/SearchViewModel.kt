package viewmodel

import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import model.ImageRepository
import model.UnsplashImage
import util.imageRepository

class SearchViewModel(
    private val repository: ImageRepository = imageRepository()
) {
    // Necessary Variables
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    // State holder
    private var _state = MutableStateFlow(SearchScreenState())
    val state = _state.asStateFlow()


    fun updateSearchKeyword(newValue: TextFieldValue) {
        _state.update {
            it.copy(
                searchKeyword = newValue
            )
        }
    }

    fun requestSearchResult() {
        _state.update {
            it.copy(
                resultImages = emptyList()
            )
        }

        // Test
        coroutineScope.launch {
            val keyword = _state.value.searchKeyword.text

            repository.fetchImageByKtor(keyword)
                .flowOn(Dispatchers.IO)
                .collectLatest {  result ->
                    result.fold(
                        onSuccess = { image ->
                            var updated = _state.value.resultImages
                            updated = updated.notifyChanges(image)

                            // for test
                            var log = _state.value.consoleLog

                            _state.update {
                                it.copy(
                                    resultImages = updated,
                                    consoleLog = "$log$image\n"
                                )
                            }
                        },
                        onFailure = {
                            println("Error: ${it.printStackTrace()}")
                        }
                    )
                }
        }
    }

    private fun List<UnsplashImage>.notifyChanges(newData: UnsplashImage): List<UnsplashImage> {
        val result = MutableList<UnsplashImage>(this.size + 1) {
            if (it < this.size) this[it]
            else newData
        }
        return result.toList()
    }


    companion object {
        private var INSTANCE: SearchViewModel? = null

        fun getInstance(): SearchViewModel {
            if (INSTANCE == null) INSTANCE = SearchViewModel()
            return INSTANCE!!
        }
    }

}

// Data class for screen state.
data class SearchScreenState(
    val searchKeyword: TextFieldValue = TextFieldValue(""),
    val resultImages: List<UnsplashImage> = emptyList(),
    val consoleLog: String = ""
)