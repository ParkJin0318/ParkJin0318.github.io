package com.parkjin.blog.components.layout

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxSize
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.fontWeight
import com.varabyte.kobweb.compose.ui.modifiers.lineHeight
import com.varabyte.kobweb.compose.ui.modifiers.margin
import com.varabyte.kobweb.compose.ui.modifiers.overflow
import com.varabyte.kobweb.compose.ui.modifiers.overflowWrap
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.document
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem

val MarkdownStyle = CssStyle {
    cssRule("h1") {
        Modifier
            .fontSize(2.5.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.5)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("h2") {
        Modifier
            .fontSize(2.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.5)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("h3") {
        Modifier
            .fontSize(1.5.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.5)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("h4") {
        Modifier
            .fontSize(1.125.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.5)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("h5") {
        Modifier
            .fontSize(0.83.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.7)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("h5") {
        Modifier
            .fontSize(0.67.cssRem)
            .fontWeight(FontWeight.Bold)
            .lineHeight(1.7)
            .margin(top = 1.625.cssRem, bottom = 1.cssRem)
    }

    cssRule("p") {
        Modifier.margin(bottom = 0.8.cssRem)
    }

    cssRule("ul") {
        Modifier.fillMaxWidth().overflowWrap(OverflowWrap.BreakWord)
    }

    cssRule(" :is(li,ol,ul)") {
        Modifier.margin(bottom = 0.25.cssRem)
    }

    cssRule("code") {
        Modifier.fontWeight(FontWeight.Medium)
    }

    cssRule("pre") {
        Modifier
            .fillMaxWidth()
            .margin(top = 0.5.cssRem, bottom = 2.cssRem)
    }

    cssRule("pre > code") {
        Modifier
            .display(DisplayStyle.Block)
            .fillMaxWidth()
            .fontSize(0.9.cssRem)
            .overflow { x(Overflow.Auto) }
    }
}

@Composable
fun MarkdownLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val context = rememberPageContext()
    val colorMode = ColorMode.current

    LaunchedEffect(colorMode) {
        var styleElement = document.querySelector("""link[title="hljs-style"]""")
        if (styleElement == null) {
            styleElement = document.createElement("link").apply {
                setAttribute("type", "text/css")
                setAttribute("rel", "stylesheet")
                setAttribute("title", "hljs-style")
            }.also { document.head!!.appendChild(it) }
        }

        styleElement.setAttribute(
            "href",
            "/highlight.js/styles/a11y-${colorMode.name.lowercase()}.min.css"
        )
    }

    LaunchedEffect(context.route) {
        js("hljs.highlightAll()")
    }

    Column(
        modifier = MarkdownStyle
            .toModifier()
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.Start,
    ) {
        content()
    }
}
