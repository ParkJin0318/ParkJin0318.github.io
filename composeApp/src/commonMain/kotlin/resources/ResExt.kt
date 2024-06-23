package resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import blog.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
internal fun Res.collectAsBytes(path: String): State<ByteArray> {
    val bytes = remember { mutableStateOf(byteArrayOf()) }
    LaunchedEffect(path) {
        bytes.value = readBytes(path)
    }
    return bytes
}
