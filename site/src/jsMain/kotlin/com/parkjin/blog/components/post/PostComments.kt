package com.parkjin.blog.components.post

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import org.jetbrains.compose.web.dom.ElementBuilder
import org.jetbrains.compose.web.dom.TagElement

@Composable
fun PostComments(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        TagElement(
            elementBuilder = ElementBuilder.createBuilder("script"),
            applyAttrs = {
                attr("src", "https://utteranc.es/client.js")
                attr("repo", "ParkJin0318/ParkJin0318.github.io")
                attr("issue-term", "pathname")
                attr("label", "comments")
                attr("theme", "github-light")
                attr("crossorigin", "anonymous")
                attr("async", "")
            },
            content = null
        )
    }
}
