package components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import blog.composeapp.generated.resources.Res
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.ImageRequest
import model.Post
import resources.collectAsBytes

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    post: Post,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = interactionSource,
            ) { onClick() }
            .padding(horizontal = 12.dp, vertical = 20.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = post.title,
                style = MaterialTheme.typography.h3,
                color = if (isHovered) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.secondary,
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                text = post.description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                text = post.createAt,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.secondary.copy(alpha = 0.7f),
            )
        }

        Spacer(
            modifier = Modifier.width(16.dp),
        )

        val bytes by Res.collectAsBytes("files/${post.thumbnail}")

        AsyncImage(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(bytes)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = "post_card_thumbnail",
        )
    }
}