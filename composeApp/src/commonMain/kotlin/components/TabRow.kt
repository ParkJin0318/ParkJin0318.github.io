package components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp

@Composable
fun TabRow(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    items: List<String>,
    onClick: (index: Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier.horizontalScroll(scrollState),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            items.forEachIndexed { index, text ->
                TabItem(
                    text = text,
                    selected = index == selectedIndex,
                    onClick = { onClick(index) },
                )
            }
        }

        Divider(color = MaterialTheme.colors.secondary.copy(alpha = 0.05f))
    }
}

@Composable
private fun TabItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val color = MaterialTheme.colors.secondary

    Text(
        modifier = Modifier
            .drawBehind {
                drawLine(
                    color = color.copy(
                        alpha = if (selected) 1f else 0f,
                    ),
                    strokeWidth = 2.dp.toPx(),
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height)
                )
            }
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                indication = LocalIndication.current,
                interactionSource = interactionSource,
            ) { onClick() }
            .padding(horizontal = 24.dp, vertical = 12.dp),
        text = text,
        style = MaterialTheme.typography.h4,
        color = color.copy(
            alpha = when {
                selected -> 1f
                isHovered -> 0.7f
                else -> 0.4f
            }
        )
    )
}
