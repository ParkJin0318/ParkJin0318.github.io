package ui.main

import model.Post

enum class MainTab(
    val title: String,
    val path: String,
    val categories: List<Post.Category>,
) {
    ALL(
        title = "전체",
        path = "all",
        categories = Post.Category.entries,
    ),
    DEV(
        title = "개발",
        path = "dev",
        categories = listOf(Post.Category.DEV),
    ),
    ETC(
        title = "기타",
        path = "etc",
        categories = listOf(Post.Category.ETC),
    )
}
