package dev.javfuentes.dailyjoke.data

import com.google.gson.annotations.SerializedName

data class JokeResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("category")
    val category: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("setup")
    val setup: String? = null,
    @SerializedName("delivery")
    val delivery: String? = null,
    @SerializedName("joke")
    val joke: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("safe")
    val safe: Boolean? = null,
    @SerializedName("lang")
    val lang: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("causedBy")
    val causedBy: List<String>? = null
)

data class Joke(
    val id: Int,
    val category: String,
    val setup: String,
    val punchline: String,
    val type: JokeType,
    val safe: Boolean,
    val lang: String
)

enum class JokeType {
    SINGLE, TWOPART
}