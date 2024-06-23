package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@Composable
fun NavigationHost(
    initPath: String,
    content: @Composable NavigationBuilder.() -> Unit
) {
    val navigator = remember { getNavigator() }

    CompositionLocalProvider(NavigatorCompositionLocal provides navigator) {
        val rawPath by navigator.getPath(initPath)
        val path = Path.from(rawPath)
        val node = remember(path) { NavigationBuilder(path.path, path) }
        node.content()
    }
}
