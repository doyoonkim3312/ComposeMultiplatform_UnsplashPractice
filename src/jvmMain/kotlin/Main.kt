import androidx.compose.material.MaterialTheme
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import screen.SearchScreen
import util.clearInstances

@Composable
@Preview
fun MainScreen() {
    MaterialTheme {
        SearchScreen()
    }
}

fun main() = application {
    Window(
        onCloseRequest = {
            clearInstances()
            exitApplication()
        },
        title = "Unsplash Image Explorer",
        state = WindowState(size = DpSize(1200.dp, 700.dp))
    ) {
        MainScreen()
    }
}
