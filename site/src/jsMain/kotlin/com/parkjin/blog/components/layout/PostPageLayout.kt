package com.parkjin.blog.components.layout

import androidx.compose.runtime.Composable
import com.parkjin.blog.Posts
import com.parkjin.blog.components.post.PostComments
import com.parkjin.blog.components.post.PostHeader
import com.parkjin.blog.model.Post
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.rememberPageContext
import org.jetbrains.compose.web.css.cssRem

@Composable
fun PostPageLayout(
    content: @Composable () -> Unit,
) {
    val context = rememberPageContext()
    val route = context.route.path

    val thumbnail = "/images${route.replace("-", "_")}/thumbnail.jpg"
    val post = Posts.find { it.route == route } ?: Post(
        route = route,
        category = "Not Found",
        title = "Not Found",
        description = "Not Found",
        createdAt = "Not Found",
        tags = emptyList(),
    )

    PageContainerLayout(post.title) {
        PostHeader(
            modifier = Modifier.margin(top = 4.cssRem),
            title = post.title,
            thumbnail = thumbnail,
            createdAt = post.createdAt,
            tags = post.tags,
        )

        MarkdownLayout(
            modifier = Modifier.margin(top = 4.cssRem),
        ) {
            content()
        }

        PostComments(
            modifier = Modifier.padding(topBottom = 4.cssRem),
        )
    }
}
