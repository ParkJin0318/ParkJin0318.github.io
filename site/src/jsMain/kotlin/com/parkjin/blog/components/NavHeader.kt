package com.parkjin.blog.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.parkjin.blog.toSitePalette
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.backgroundColor
import com.varabyte.kobweb.compose.ui.modifiers.borderTop
import com.varabyte.kobweb.compose.ui.modifiers.color
import com.varabyte.kobweb.compose.ui.modifiers.cursor
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.minHeight
import com.varabyte.kobweb.compose.ui.modifiers.onClick
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.modifiers.position
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.compose.ui.modifiers.top
import com.varabyte.kobweb.compose.ui.modifiers.zIndex
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.cssRem

@Composable
fun NavHeader(
    modifier: Modifier = Modifier,
) {
    val palette = ColorMode.current.toSitePalette()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .minHeight(3.75.cssRem)
            .position(Position.Sticky)
            .top(0.cssRem)
            .zIndex(9998)
            .backgroundColor(palette.backgroundColor),
    ) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(leftRight = 2.cssRem),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Title()

            Spacer()

            Row(
                modifier = Modifier
                    .fontSize(1.5.cssRem)
                    .gap(1.cssRem),
                verticalAlignment = Alignment.CenterVertically
            ) {
                GithubButton()
                ColorModeButton()
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
private fun Title() {
    val context = rememberPageContext()
    val palette = ColorMode.current.toSitePalette()

    Row(
        Modifier
            .cursor(Cursor.Pointer)
            .onClick { context.router.tryRoutingTo("/") },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SpanText(
            text = "Jin ",
            modifier = Modifier
                .fontSize(1.25.cssRem)
                .fontWeight(FontWeight.Bold)
                .color(palette.contentColor)
        )

        SpanText(
            text = "Blog",
            modifier = Modifier
                .fontSize(1.25.cssRem)
                .fontWeight(FontWeight.Normal)
                .color(palette.contentSecondaryColor)
        )
    }
}

@Composable
private fun GithubButton() {
    val context = rememberPageContext()

    Box(
        modifier = Modifier.size(1.5.cssRem)
            .cursor(Cursor.Pointer)
            .onClick {
                context.router.navigateTo("https://github.com/parkjin0318")
            },
        contentAlignment = Alignment.Center,
    ) {
        val colorMode by ColorMode.currentState
        val isDark = if (colorMode.isDark) "-white" else ""

        Image(
            modifier = Modifier.fillMaxSize(),
            src = "/github-mark$isDark.svg",
            description = "github-mark",
        )
    }
}

@Composable
private fun ColorModeButton() {
    var colorMode by ColorMode.currentState

    Box(
        modifier = Modifier.size(1.5.cssRem)
            .cursor(Cursor.Pointer)
            .onClick { colorMode = colorMode.opposite },
        contentAlignment = Alignment.Center,
    ) {
        if (colorMode.isLight) {
            MoonIcon(Modifier.fillMaxSize())
        } else {
            SunIcon(Modifier.fillMaxSize())
        }
    }
}
