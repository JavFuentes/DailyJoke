package dev.javfuentes.dailyjoke.utils

object TextUtils {
    
    fun cleanText(text: String): String {
        return text
            .trim()
            .replace(Regex("\\s+"), " ")
            .replace(Regex("[^\\p{L}\\p{N}\\p{P}\\p{Z}]"), "")
    }
}