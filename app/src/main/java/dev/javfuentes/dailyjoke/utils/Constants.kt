package dev.javfuentes.dailyjoke.utils

object Constants {
    const val BASE_URL = "https://sv443.net/jokeapi/v2/"
    const val NETWORK_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    
    // API Parameters
    const val JOKE_TYPE_TWOPART = "twopart"
    const val JOKE_TYPE_SINGLE = "single"
    const val SAFE_MODE = "true"
    const val DEFAULT_LANGUAGE = "en"
    
    // Categories
    val JOKE_CATEGORIES = listOf(
        "Programming",
        "Miscellaneous", 
        "Dark",
        "Pun",
        "Spooky",
        "Christmas"
    )
}