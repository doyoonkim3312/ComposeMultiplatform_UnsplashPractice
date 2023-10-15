package screen

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import util.searchViewModel
import viewmodel.SearchViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = searchViewModel()
) {
    val state by viewModel.state.collectAsState()

    // LaunchedEffect
    LaunchedEffect(Unit) {
        snapshotFlow { state.searchKeyword }
            .debounce(500L)
            .distinctUntilChanged()
            .filter { it.text.isNotBlank() }
            .collectLatest {
//                println("Search will be initiated for keyword: ${state.searchKeyword.text}")
                viewModel.requestSearchResult()
            }
    }

    Column(
        modifier = modifier.fillMaxSize()
            .background(MaterialTheme.colors.surface),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            value = state.searchKeyword,
            onValueChange = { viewModel.updateSearchKeyword(it) },
            singleLine = true,
            shape = RoundedCornerShape(15.dp)
        )

        // LazyGrid
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            verticalArrangement = Arrangement.spacedBy(2.dp),
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            state = rememberLazyStaggeredGridState(),
            content = {
                items(
                    items = state.resultImages
                ) {
                    ImageCard(data = it)
                }
            }
        )
    }

}

@Preview
@Composable
fun SearchScreen_Preview() {
    Box(
        Modifier.fillMaxSize()
    ) {
        SearchScreen()
    }
}