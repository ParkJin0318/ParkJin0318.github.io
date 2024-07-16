package com.parkjin.blog.components.post

import androidx.compose.runtime.Composable
import com.parkjin.blog.model.Post
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.transform
import com.varabyte.kobweb.compose.ui.modifiers.transition
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.selectors.hover
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.s

const val PostCardScale = 1.03

val PostCardStyle = CssStyle {
    base {
        Modifier
            .color(colorMode.toSitePalette().contentColor)
            .transform { scale(1) }
            .transition(Transition.of("ease-in-out", duration = 0.2.s))
    }

    hover {
        Modifier
            .cursor(Cursor.Pointer)
            .color(colorMode.toSitePalette().primaryColor)
            .transform { scale(PostCardScale) }
    }
}

@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    post: Post,
    onClick: () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        modifier = PostCardStyle.toModifier()
            .then(modifier)
            .onClick { onClick() },
    ) {
        Column {
            SpanText(
                text = post.title,
                modifier = Modifier
                    .fontSize(1.25.cssRem)
                    .fontWeight(FontWeight.Bold),
            )

            SpanText(
                text = post.description,
                modifier = Modifier
                    .fontSize(1.cssRem)
                    .fontWeight(FontWeight.Normal)
                    .color(palette.contentSecondaryColor)
                    .margin(top = 0.5.cssRem),
            )

            SpanText(
                text = post.createdAt,
                modifier = Modifier
                    .fontSize(0.9.cssRem)
                    .fontWeight(FontWeight.Normal)
                    .color(palette.contentSecondaryColor)
                    .margin(top = 0.5.cssRem),
            )
        }

        Spacer()

        Image(
            modifier = Modifier
                .width(8.125.cssRem)
                .height(6.25.cssRem)
                .margin(left = 0.5.cssRem)
                .objectFit(ObjectFit.Contain),
            src = "/images${post.route.replace("-", "_")}/thumbnail.jpg",
            description = "thumbnail",
        )
    }
}
