package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize

@Composable
internal fun ContainerPage(
    header: @Composable ColumnScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) {
    val calculator = remember { Calculator() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = calculator.fraction(Type.HEADER)),
        ) {
            header()
        }

        Divider(color = MaterialTheme.colors.secondary.copy(alpha = 0.05f))

        Column(
            modifier = Modifier
                .fillMaxWidth(fraction = calculator.fraction(Type.CONTENT))
                .fillMaxHeight(),
        ) {
            content()
        }
    }
}

private enum class Type(
    val min: Float,
    val weight: Float,
) {
    HEADER(min = 0.9f, weight = 4f),
    CONTENT(min = 0.7f, weight = 1f),
}

private class Calculator {

    companion object {
        private const val MAX_WIDTH = 10000f
    }

    @ReadOnlyComposable
    @Composable
    fun fraction(type: Type): Float {
        val width = containerSize().width.toFloat()

        return when {
            width > MAX_WIDTH -> type.min
            else -> 1f - width.div(MAX_WIDTH * type.weight)
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @ReadOnlyComposable
    @Composable
    private fun containerSize(): IntSize {
        val windowInfo = LocalWindowInfo.current
        return windowInfo.containerSize
    }
}
