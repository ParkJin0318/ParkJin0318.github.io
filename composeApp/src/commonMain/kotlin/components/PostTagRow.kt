package components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PostTagRow(
    modifier: Modifier = Modifier,
    tags: List<String>,
) {
    Row {
        tags.forEachIndexed { index, tag ->
            TagItem(
                modifier = modifier.padding(
                    start = if (index == 0) 0.dp else 8.dp,
                ),
                tag = tag,
            )
        }
    }
}

@Composable
private fun TagItem(
    modifier: Modifier = Modifier,
    tag: String,
) {
    Text(
        modifier = modifier
            .background(
                color = MaterialTheme.colors.primary.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.medium,
            )
            .padding(horizontal = 8.dp, vertical = 4.dp),
        text = "#$tag",
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.secondary,
    )
}
