package components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import blog.composeapp.generated.resources.Res
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import com.mikepenz.markdown.model.ImageData
import com.mikepenz.markdown.model.ImageTransformer
import resources.collectAsBytes

@Composable
fun MarkdownContent(
    modifier: Modifier = Modifier,
    content: String,
) {
    Markdown(
        modifier = modifier,
        content = content,
        colors = DefaultMarkdownColors(
            text = MaterialTheme.colors.secondary,
            codeText = MaterialTheme.colors.secondary,
            inlineCodeText = MaterialTheme.colors.secondary,
            linkText = MaterialTheme.colors.secondary,
            codeBackground = MaterialTheme.colors.secondary,
            inlineCodeBackground = MaterialTheme.colors.secondary,
            dividerColor = MaterialTheme.colors.secondary,
        ),
        typography = DefaultMarkdownTypography(
            h1 = MaterialTheme.typography.h1,
            h2 = MaterialTheme.typography.h2,
            h3 = MaterialTheme.typography.h3,
            h4 = MaterialTheme.typography.h4,
            h5 = MaterialTheme.typography.h5,
            h6 = MaterialTheme.typography.h6,
            text = MaterialTheme.typography.body2,
            code = MaterialTheme.typography.body2,
            quote = MaterialTheme.typography.body2,
            paragraph = MaterialTheme.typography.body2,
            ordered = MaterialTheme.typography.body2,
            bullet = MaterialTheme.typography.body2,
            list = MaterialTheme.typography.body2,
        ),
        imageTransformer = LocalImageTransformer,
    )
}

private object LocalImageTransformer : ImageTransformer {

    @Composable
    override fun transform(link: String): ImageData {
        val path = "files/${link.removePrefix("../")}"
        val bytes by Res.collectAsBytes(path)

        return rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(bytes)
                .size(coil3.size.Size.ORIGINAL)
                .build()
        ).let { ImageData(it) }
    }

    @Composable
    override fun intrinsicSize(painter: Painter): Size {
        var size by remember(painter) { mutableStateOf(painter.intrinsicSize) }

        if (painter is AsyncImagePainter) {
            LaunchedEffect(painter.state) {
                painter.state.painter?.let {
                    size = it.intrinsicSize
                }
            }
        }

        return size
    }
}
