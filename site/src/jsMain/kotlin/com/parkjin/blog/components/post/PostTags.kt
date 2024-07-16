package com.parkjin.blog.components.post

import androidx.compose.runtime.Composable
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.JustifyContent
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.flexWrap
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.justifyContent
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.cssRem

@Composable
fun PostTags(
    modifier: Modifier = Modifier,
    tags: List<String>,
    selectedTag: String? = null,
    onClick: ((String) -> Unit)? = null,
) {
    val palette = ColorMode.current.toSitePalette()

    Row(
        modifier = Modifier
            .display(DisplayStyle.Flex)
            .flexWrap(FlexWrap.Wrap)
            .justifyContent(JustifyContent.Start)
            .gap(0.5.cssRem)
            .then(modifier),
    ) {
        tags.forEach { tag ->
            SpanText(
                text = "#$tag",
                modifier = Modifier
                    .fontSize(0.9.cssRem)
                    .borderRadius(0.3.cssRem)
                    .margin(right = 0.3.cssRem)
                    .padding(leftRight = 0.5.cssRem, topBottom = 0.25.cssRem)
                    .then(if (onClick != null) Modifier.cursor(Cursor.Pointer) else Modifier)
                    .color(
                        if (tag == selectedTag) {
                            palette.primaryColor
                        } else {
                            palette.tagColor
                        }
                    )
                    .backgroundColor(
                        if (tag == selectedTag) {
                            palette.primaryBackgroundColor
                        } else {
                            palette.tagBackgroundColor
                        }
                    )
                    .onClick { onClick?.invoke(tag) },
            )
        }
    }
}
