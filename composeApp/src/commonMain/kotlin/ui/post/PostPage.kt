package ui.post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import blog.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import components.ContainerPage
import components.Header
import components.MarkdownContent
import components.PostTagRow
import model.Post
import model.collectAsPosts
import okio.internal.commonToUtf8String
import resources.collectAsBytes

@Composable
fun PostPage(
    path: String,
    onClickTitle: () -> Unit,
    onClickGithub: () -> Unit,
) {
    val scrollState = rememberScrollState()
    val contentBytes by Res.collectAsBytes("files/$path/index.md")

    val posts by collectAsPosts()
    val post = posts.items.find { it.path == path }

    ContainerPage(
        header = {
            Header(
                modifier = Modifier.padding(vertical = 12.dp),
                onClickTitle = onClickTitle,
                onClickGithub = onClickGithub,
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
        ) {
            if (post != null) {
                PostHeader(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    post = post,
                )
            }

            MarkdownContent(
                modifier = Modifier.padding(top = 40.dp),
                content = contentBytes.commonToUtf8String(),
            )
        }
    }
}

@Composable
private fun PostHeader(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Column(
        modifier = modifier,
    ) {
        val imageBytes by Res.collectAsBytes("files/${post.thumbnail}")

        AsyncImage(
            modifier = Modifier
                .height(300.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(imageBytes)
                .build(),
            contentDescription = "post_thumbnail",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            text = post.title,
            style = MaterialTheme.typography.h1,
            color = MaterialTheme.colors.secondary,
        )

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            text = post.createAt,
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
        )

        PostTagRow(
            modifier = Modifier.padding(top = 16.dp),
            tags = post.tags,
        )
    }
}
