package model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import blog.composeapp.generated.resources.Res
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okio.internal.commonToUtf8String
import resources.collectAsBytes

@Serializable
data class Posts(
    @SerialName("posts") val items: List<Post>,
)

@Composable
fun collectAsPosts(): State<Posts> {
    val posts = remember { mutableStateOf(Posts(emptyList())) }
    val bytes by Res.collectAsBytes("files/posts.json")
    LaunchedEffect(bytes) {
        posts.value = runCatching { Json.decodeFromString<Posts>(bytes.commonToUtf8String()) }
            .getOrDefault(Posts(emptyList()))
    }
    return posts
}
