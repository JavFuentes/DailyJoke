package dev.javfuentes.dailyjoke.data

import com.google.gson.annotations.SerializedName

data class Joke(
    @SerializedName("type")
    val type: String,
    @SerializedName("setup")
    val setup: String,
    @SerializedName("delivery")
    val delivery: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("safe")
    val safe: Boolean,
    @SerializedName("lang")
    val lang: String
)

data class JokeResponse(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("type")
    val type: String?,
    @SerializedName("setup")
    val setup: String?,
    @SerializedName("delivery")
    val delivery: String?,
    @SerializedName("category")
    val category: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("safe")
    val safe: Boolean?,
    @SerializedName("lang")
    val lang: String?,
    @SerializedName("joke")
    val joke: String?,
    @SerializedName("message")
    val message: String?
)