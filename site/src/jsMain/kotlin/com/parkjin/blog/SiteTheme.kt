package com.parkjin.blog

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color

class SitePalette(
    val primaryColor: Color,
    val primaryBackgroundColor: Color,
    val contentColor: Color,
    val contentSecondaryColor: Color,
    val backgroundColor: Color,
    val tagColor: Color,
    val tagBackgroundColor: Color,
    val dividerColor: Color,
)

object SitePalettes {
    val light = SitePalette(
        primaryColor = Color.rgb(0x00296FCF),
        primaryBackgroundColor = Color.rgb(0x00B9D2F4),
        contentColor = Color.rgb(0x00191f28),
        contentSecondaryColor = Color.rgb(0x004e5968),
        backgroundColor = Color.rgb(0x00FFFFFF),
        tagColor = Color.rgb(0x00191f28),
        tagBackgroundColor = Color.rgb(0x00f2f4f6),
        dividerColor = Color.rgb(0x00E0E1E3),
    )
    val dark = SitePalette(
        primaryColor = Color.rgb(0x00296FCF),
        primaryBackgroundColor = Color.rgb(0x00B9D2F4),
        contentColor = Color.rgb(0x00FFFFFF),
        contentSecondaryColor = Color.rgb(0x00C9D1DD),
        backgroundColor = Color.rgb(0x002B2B2B),
        tagColor = Color.rgb(0x00191f28),
        tagBackgroundColor = Color.rgb(0x00f2f4f6),
        dividerColor = Color.rgb(0x006C6E6F),
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = SitePalettes.light.backgroundColor
    ctx.theme.palettes.light.color = SitePalettes.light.contentColor
    ctx.theme.palettes.dark.background = SitePalettes.dark.backgroundColor
    ctx.theme.palettes.dark.color = SitePalettes.dark.contentColor
}
