package resources

import androidx.compose.foundation.LocalIndication
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun BlogTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colors = blogColors(),
        typography = blogTypography(),
        shapes = blogShape(),
    ) {
        val rippleIndication = rememberRipple(
            color = MaterialTheme.colors.secondary.copy(alpha = 0.2f),
        )

        CompositionLocalProvider(
            LocalIndication provides rippleIndication,
        ) {
            content()
        }
    }
}
