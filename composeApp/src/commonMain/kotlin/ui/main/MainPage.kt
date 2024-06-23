package ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import components.ContainerPage
import components.Header
import components.PostCard
import components.TabRow
import model.Post
import model.collectAsPosts


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainPage(
    initTab: MainTab,
    onTabChanged: (MainTab) -> Unit,
    onClickTitle: () -> Unit,
    onClickGithub: () -> Unit,
    onClickPost: (Post) -> Unit,
) {
    var mainTab by collectAsMainTab(initTab)
    val posts by collectAsPosts()

    ContainerPage(
        header = {
            Header(
                modifier = Modifier.padding(vertical = 12.dp),
                onClickTitle = onClickTitle,
                onClickGithub = onClickGithub,
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
        ) {
            item {
                Text(
                    modifier = Modifier
                        .padding(vertical = 20.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .background(MaterialTheme.colors.primary)
                        .padding(vertical = 48.dp),
                    textAlign = TextAlign.Center,
                    text = buildAnnotatedString {
                        val style = MaterialTheme.typography.h2
                        withStyle(style = style.toSpanStyle()) {
                            append("Jin")
                        }
                        append(" ")
                        withStyle(
                            style = style.copy(fontWeight = FontWeight.Normal).toSpanStyle()
                        ) {
                            append("Blog")
                        }
                    },
                    color = MaterialTheme.colors.surface,
                )
            }

            stickyHeader {
                TabRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.background)
                        .padding(top = 12.dp),
                    selectedIndex = mainTab.ordinal,
                    items = MainTab.entries.map { it.title },
                    onClick = {
                        mainTab = MainTab.entries[it]
                        onTabChanged(mainTab)
                    },
                )
            }

            itemsIndexed(
                items = posts.items.filter { it.category in mainTab.categories },
                key = { _, item -> item.path }
            ) { index, post ->
                PostCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = if (index == 0) 20.dp else 0.dp,
                            bottom = if (index == posts.items.lastIndex) 20.dp else 0.dp,
                        ),
                    post = post,
                    onClick = { onClickPost(post) },
                )
            }
        }
    }
}

@Composable
private fun collectAsMainTab(initTab: MainTab): MutableState<MainTab> {
    val mainTab = remember { mutableStateOf(initTab) }
    LaunchedEffect(initTab) {
        mainTab.value = initTab
    }
    return mainTab
}
