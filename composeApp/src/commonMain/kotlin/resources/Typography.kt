package resources

import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import blog.composeapp.generated.resources.Res
import blog.composeapp.generated.resources.pretendard_bold
import blog.composeapp.generated.resources.pretendard_medium
import blog.composeapp.generated.resources.pretendard_regular
import blog.composeapp.generated.resources.pretendard_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun blogFontFamily() = FontFamily(
    Font(Res.font.pretendard_bold, FontWeight.Bold),
    Font(Res.font.pretendard_semibold, FontWeight.SemiBold),
    Font(Res.font.pretendard_medium, FontWeight.Medium),
    Font(Res.font.pretendard_regular, FontWeight.Normal),
)

@Composable
fun blogTypography() = Typography().run {
    val fontFamily = blogFontFamily()

    copy(
        h1 = h1.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 32.sp,
            lineHeight = 40.sp,
        ),
        h2 = h2.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 24.sp,
            lineHeight = 30.sp,
        ),
        h3 = h3.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 20.sp,
            lineHeight = 25.sp,
        ),
        h4 = h4.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            lineHeight = 20.sp,
        ),
        h5 = h5.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 14.sp,
            lineHeight = 17.5.sp,
        ),
        h6 = h6.copy(
            fontWeight = FontWeight.SemiBold,
            fontFamily = fontFamily,
            fontSize = 13.6.sp,
            lineHeight = 17.sp,
        ),
        subtitle1 = subtitle1.copy(
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        subtitle2 = subtitle2.copy(
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            fontSize = 14.sp,
            lineHeight = 24.sp,
        ),
        body1 = body1.copy(
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
            fontSize = 16.sp,
            lineHeight = 24.sp,
        ),
        body2 = body2.copy(
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
            fontSize = 14.sp,
            lineHeight = 20.sp,
        ),
        button = button.copy(
            fontWeight = FontWeight.Medium,
            fontFamily = fontFamily,
            fontSize = 14.sp,
            lineHeight = 16.sp,
        ),
        caption = caption.copy(
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
            fontSize = 12.sp,
            lineHeight = 16.sp,
        ),
        overline = overline.copy(
            fontWeight = FontWeight.Normal,
            fontFamily = fontFamily,
            fontSize = 10.sp,
            lineHeight = 16.sp,
        ),
    )
}
