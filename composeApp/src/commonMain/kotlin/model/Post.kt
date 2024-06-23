package model

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val title: String,
    val description: String,
    val path: String,
    val thumbnail: String,
    val createAt: String,
    val category: Category,
    val tags: List<String>,
) {
    enum class Category {
        DEV, ETC;
    }
}
