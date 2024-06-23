package components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import blog.composeapp.generated.resources.Res
import blog.composeapp.generated.resources.github_logo
import org.jetbrains.compose.resources.painterResource

@Composable
fun Header(
    modifier: Modifier = Modifier,
    onClickTitle: () -> Unit,
    onClickGithub: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable { onClickTitle() }
                .padding(8.dp),
            text = buildAnnotatedString {
                val style = MaterialTheme.typography.h3
                withStyle(style = style.toSpanStyle()) {
                    append("Jin")
                }
                append(" ")
                withStyle(style = style.copy(fontWeight = FontWeight.Normal).toSpanStyle()) {
                    append("Blog")
                }
            },
            color = MaterialTheme.colors.secondary,
        )

        Spacer(
            modifier = Modifier.weight(1f),
        )

        Row(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .background(color = MaterialTheme.colors.secondary.copy(alpha = 0.03f))
                .clickable { onClickGithub() }
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.github_logo),
                tint = MaterialTheme.colors.secondary,
                contentDescription = "github_logo",
            )

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "바로가기",
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
            )
        }
    }
}
