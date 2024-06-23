package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Navigation
class NavigationBuilder internal constructor(
    private val basePath: String,
    private val remainingPath: Path
) {
    private var match by mutableStateOf(Match.NoMatch)

    private enum class Match {
        Constant, Integer, String, NoMatch
    }

    @Navigation
    @Composable
    fun composable(
        vararg path: String,
        nestedPath: @Composable NavigationBuilder.() -> Unit
    ) {
        val relaxedPath = path.check()
        val currentPath = remainingPath.currentPath
        if ((match == Match.NoMatch || match == Match.Constant) && currentPath in relaxedPath) {
            execute(currentPath, nestedPath)
            match = Match.Constant
        }
    }

    private fun Array<out String>.check(): List<String> {
        val relaxedPath = map { it.removePrefix("/").removeSuffix("/") }
        require(relaxedPath.none { it.contains("/") }) { "To use nested paths, use path() { path() { } } instead." }
        return relaxedPath
    }

    @Composable
    private fun execute(
        currentPath: String,
        nestedPath: @Composable NavigationBuilder.() -> Unit
    ) {
        val newPath = remainingPath.newPath(currentPath)
        val currentNavigator = Navigator.current
        val delegatingNavigator =
            remember(newPath) { DelegateNavigator(basePath, currentNavigator) }
        CompositionLocalProvider(NavigatorCompositionLocal provides delegatingNavigator) {
            val newState = NavigationBuilder(basePath, newPath)
            newState.nestedPath()
        }
    }

    @Navigation
    @Composable
    fun string(nestedPath: @Composable NavigationBuilder.(String) -> Unit) {
        val currentPath = remainingPath.currentPath
        if ((match == Match.NoMatch || match == Match.String) && currentPath.isNotEmpty()) {
            execute(currentPath) {
                nestedPath(currentPath)
            }
            match = Match.String
        }
    }

    @Navigation
    @Composable
    fun int(nestedPath: @Composable NavigationBuilder.(Int) -> Unit) {
        val currentPath = remainingPath.currentPath
        val int = currentPath.toIntOrNull()
        if ((match == Match.NoMatch || match == Match.Integer) && int != null) {
            execute(currentPath) {
                nestedPath(int)
            }
            match = Match.Integer
        }
    }

    @Navigation
    @Composable
    fun noMatch(redirectPath: String) {
        if (match == Match.NoMatch) {
            val router = Navigator.current

            LaunchedEffect(Unit) {
                router.navigate(redirectPath)
            }
        }
    }

    private class DelegateNavigator(
        private val basePath: String,
        private val navigator: Navigator
    ) : Navigator by navigator {

        override fun navigate(path: String) {
            when {
                path.startsWith("/") -> {
                    navigator.navigate(path)
                }

                basePath == "/" -> {
                    navigator.navigate("/$path")
                }

                path.startsWith(".") -> {
                    val newPath = navigator.currentPath.relative(path)
                    navigator.navigate(newPath.path)
                }

                else -> {
                    navigator.navigate("$basePath/$path")
                }
            }
        }
    }
}
