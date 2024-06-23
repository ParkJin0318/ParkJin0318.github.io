package navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.compositionLocalOf

interface Navigator {

    val currentPath: Path

    fun navigate(path: String)

    @Composable
    fun getPath(initPath: String): State<String>

    companion object {
        val current: Navigator
            @Composable
            get() = NavigatorCompositionLocal.current
    }
}

expect fun getNavigator(): Navigator

internal val NavigatorCompositionLocal: ProvidableCompositionLocal<Navigator> =
    compositionLocalOf { error("Navigator not defined, cannot provide through NavigatorCompositionLocal.") }
