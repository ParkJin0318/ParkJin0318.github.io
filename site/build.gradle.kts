import com.varabyte.kobweb.gradle.application.extensions.AppBlock.LegacyRouteRedirectStrategy
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

val blogPackage = "com.parkjin.blog"
group = blogPackage
version = "1.0-SNAPSHOT"

private data class MarkdownContent(
    private val route: String,
    private val category: String,
    private val title: String,
    private val description: String,
    val createdAt: String,
    private val tags: List<String>,
) {
    fun toPost(): String {
        return """com.parkjin.blog.model.Post(
        route = "$route",
        category = "$category",
        title = "$title",
        description = "$description",
        createdAt = "$createdAt",
        tags = listOf(${tags.joinToString(", ") { "\"$it\"" }}),
    )""".trimMargin()
    }
}

kobweb {
    app {
        index {
            head.add {
                link(rel = "stylesheet", href = "/fonts/pretendard.css")

                script {
                    src = "/highlight.js/highlight.min.js"
                }
            }
        }

        legacyRouteRedirectStrategy.set(LegacyRouteRedirectStrategy.DISALLOW)
    }

    markdown {
        process.set { markdownEntries ->
            val posts = markdownEntries.map { markdownEntry ->
                val frontMatter = markdownEntry.frontMatter

                MarkdownContent(
                    route = markdownEntry.route,
                    title = frontMatter["title"]?.singleOrNull() ?: "",
                    category = frontMatter["category"]?.singleOrNull() ?: "",
                    description = frontMatter["description"]?.singleOrNull() ?: "",
                    createdAt = frontMatter["createdAt"]?.singleOrNull() ?: "",
                    tags = frontMatter["tags"] ?: emptyList(),
                )
            }

            val postsPath = "${blogPackage.replace('.', '/')}/Posts.kt"

            generateKotlin(postsPath, buildString {
                appendLine(
                    """
                        package $blogPackage   

                        val Posts: List<com.parkjin.blog.model.Post> = listOf(
                        """.trimIndent()
                )

                posts.sortedByDescending { it.createdAt }
                    .forEach { entry ->
                        appendLine("    ${entry.toPost()},")
                    }

                appendLine(
                    """
                        )
                        """.trimIndent()
                )
            })
        }
    }
}

kotlin {
    configAsKobwebApplication("blog" /*, includeServer = true*/)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.kobwebx.markdown)
        }
    }
}
