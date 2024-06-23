package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import kotlinx.browser.window

actual fun getNavigator(): Navigator = object : Navigator {

    override val currentPath: Path
        get() = Path.from(currentHash.value)

    private val currentHash: MutableState<String> =
        mutableStateOf(window.location.hash.currentURL() ?: "")

    @Composable
    override fun getPath(initPath: String): State<String> {
        LaunchedEffect(Unit) {
            currentHash.value = window.location.hash.currentURL() ?: initPath
            window.onhashchange = {
                currentHash.value = window.location.hash.currentURL() ?: ""
            }
        }
        return currentHash
    }

    private fun String.currentURL() = removePrefix("#")
        .removePrefix("/")
        .ifBlank { null }

    override fun navigate(path: String) {
        if (window.location.hash.currentURL() == path.currentURL()) {
            currentHash.value = path.removePrefix("#")
        } else {
            window.location.hash = path
        }
    }
}
