package com.parkjin.blog.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.parkjin.blog.Posts

@Composable
fun rememberPostCollection(): PostCollection {
    return remember { PostCollection() }
}

class PostCollection {

    val categories = Posts.map { it.category }.distinct()
    val tags = Posts.flatMap { it.tags }.distinct()

    var posts by mutableStateOf(emptyList<Post>())
        private set

    var selectedCategory: String? by mutableStateOf(null)
        private set

    var selectedTag: String? by mutableStateOf(null)
        private set

    init {
        notify(category = categories.firstOrNull())
    }

    fun notify(
        category: String? = selectedCategory,
        tag: String? = selectedTag
    ) {
        selectedCategory = category
        selectedTag = if (selectedTag == tag) null else tag

        posts = Posts
            .filter { it.category == category }
            .filter { if (selectedTag == null) true else it.tags.contains(selectedTag) }
    }
}
