package com.parkjin.blog.pages

import androidx.compose.runtime.Composable
import com.parkjin.blog.components.TabRow
import com.parkjin.blog.components.layout.PageContainerLayout
import com.parkjin.blog.components.post.PostCard
import com.parkjin.blog.components.post.PostCardScale
import com.parkjin.blog.components.post.PostTags
import com.parkjin.blog.model.rememberPostCollection
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.borderLeft
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxHeight
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.maxWidth
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.displayIfAtLeast
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem

@Page
@Composable
fun IndexPage() {
    PageContainerLayout {
        val context = rememberPageContext()
        val postCollection = rememberPostCollection()

        val palette = ColorMode.current.toSitePalette()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 2.cssRem, bottom = 4.cssRem)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TabRow(
                    tabs = postCollection.categories,
                    selectedTab = postCollection.selectedCategory,
                    onTabSelected = { postCollection.notify(category = it) }
                )

                postCollection.posts.forEach { post ->
                    PostCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(PostCardScale.cssRem),
                        post = post,
                        onClick = { context.router.tryRoutingTo(post.route) },
                    )
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .margin(leftRight = 2.cssRem)
                    .borderLeft(0.063.cssRem, LineStyle.Solid, palette.dividerColor)
                    .displayIfAtLeast(Breakpoint.MD)
            )

            Column(
                modifier = Modifier
                    .maxWidth(20.cssRem)
                    .displayIfAtLeast(Breakpoint.MD)
            ) {
                SpanText(
                    text = "태그",
                    modifier = Modifier
                        .fontSize(0.9.cssRem)
                        .fontWeight(FontWeight.Normal)
                        .color(palette.contentSecondaryColor)
                )

                PostTags(
                    modifier = Modifier.margin(top = 1.cssRem),
                    tags = postCollection.tags,
                    selectedTag = postCollection.selectedTag,
                    onClick = { postCollection.notify(tag = it) },
                )
            }
        }
    }
}
