package com.example.redditapp.model

import com.google.gson.annotations.SerializedName

const val POST_HINT_IMAGE = "image"

data class PostModel(
    @SerializedName("id")
    val id: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("author")
    val author: String?,

    @SerializedName("thumbnail")
    val thumbnail: String?,

    @SerializedName("url")
    val url: String?,

    @SerializedName("num_comments")
    val numComments: Int?,

    @SerializedName("created")
    val postTime: Double?,

    @SerializedName("is_video")
    val isVideo: Boolean,

    @SerializedName("post_hint")
    val postHint: String?
) {
    fun isImage() = !isVideo && postHint?.equals(POST_HINT_IMAGE) ?: false
}