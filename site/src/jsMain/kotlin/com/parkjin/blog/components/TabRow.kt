package com.parkjin.blog.components

import androidx.compose.runtime.Composable
import com.parkjin.blog.components.post.PostCardScale
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.silk.components.layout.DividerVars
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    tabs: List<String> = emptyList(),
    selectedTab: String? = null,
    onTabSelected: (String) -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .position(Position.Sticky)
            .top(3.75.cssRem)
            .zIndex(9999)
            .backgroundColor(palette.backgroundColor)
            .padding(leftRight = PostCardScale.cssRem)
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            tabs.forEach { tab ->
                TabItem(
                    text = tab,
                    selected = selectedTab == tab,
                    onClick = { onTabSelected(tab) },
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .borderTop(0.063.cssRem, LineStyle.Solid, palette.dividerColor)
        )
    }
}

@Composable
private fun TabItem(
    modifier: Modifier = Modifier,
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val palette = ColorMode.current.toSitePalette()
    val dividerColor = if (selected) palette.contentColor else Colors.Transparent

    Column(
        modifier = modifier.onClick { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SpanText(
            text = text,
            modifier = Modifier
                .cursor(Cursor.Pointer)
                .padding(leftRight = 1.cssRem, top = 1.5.cssRem)
                .fontSize(1.1.cssRem)
                .fontWeight(
                    if (selected) FontWeight.Bold else FontWeight.Medium
                )
                .color(
                    if (selected) palette.contentColor else palette.contentSecondaryColor
                ),
        )

        Box(
            modifier = Modifier
                .margin(top = 0.5.cssRem)
                .borderRadius(topLeft = 0.3.cssRem, topRight = 0.3.cssRem)
                .borderTop(0.125.cssRem, LineStyle.Solid, dividerColor)
                .width(DividerVars.Length.value())
        )
    }
}
