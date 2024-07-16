package com.parkjin.blog.components.post

import androidx.compose.runtime.Composable
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.objectFit
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem

@Composable
fun PostHeader(
    modifier: Modifier = Modifier,
    title: String,
    thumbnail: String?,
    createdAt: String,
    tags: List<String>,
) {
    val palette = ColorMode.current.toSitePalette()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier),
    ) {
        if (thumbnail != null) {
            Image(
                modifier = Modifier
                    .width(20.cssRem)
                    .objectFit(ObjectFit.Contain),
                src = thumbnail,
                description = "thumbnail",
            )
        }

        SpanText(
            text = title,
            modifier = Modifier
                .fontSize(3.cssRem)
                .fontWeight(FontWeight.Bold)
                .margin(top = 2.cssRem)
                .color(palette.contentColor),
        )

        PostTags(
            tags = tags,
            modifier = Modifier
                .fillMaxWidth()
                .margin(top = 0.8.cssRem),
        )

        SpanText(
            text = createdAt,
            modifier = Modifier
                .fontSize(0.9.cssRem)
                .fontWeight(FontWeight.Normal)
                .color(palette.contentSecondaryColor)
                .margin(top = 1.cssRem),
        )
    }
}
