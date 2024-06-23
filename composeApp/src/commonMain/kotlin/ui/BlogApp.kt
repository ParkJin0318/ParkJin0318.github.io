package ui

import androidx.compose.runtime.Composable
import kotlinx.browser.window
import navigation.NavigationHost
import navigation.NavigatorCompositionLocal
import ui.main.MainPage
import ui.main.MainTab
import ui.post.PostPage

@Composable
fun BlogApp() {
    val onClickGithub = {
        window.location.href = "https://github.com/parkjin0318"
    }

    NavigationHost(
        initPath = MainTab.ALL.path,
    ) {
        val navigator = NavigatorCompositionLocal.current

        composable("posts") {
            string { path ->
                val initTab = MainTab.entries
                    .find { it.path == path }
                    ?: MainTab.entries.first()

                MainPage(
                    initTab = initTab,
                    onTabChanged = { navigator.navigate("posts/${it.path}") },
                    onClickTitle = { navigator.navigate(initTab.path) },
                    onClickGithub = onClickGithub,
                    onClickPost = { navigator.navigate("post/${it.path}") },
                )
            }
        }

        composable("post") {
            string { path ->
                PostPage(
                    path = path,
                    onClickTitle = { navigator.navigate(MainTab.ALL.path) },
                    onClickGithub = onClickGithub,
                )
            }
        }

        noMatch(
            redirectPath = "posts/${MainTab.entries.first().path}",
        )
    }
}
