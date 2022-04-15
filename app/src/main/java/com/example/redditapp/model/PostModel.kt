package com.example.redditapp.model

import com.google.gson.annotations.SerializedName

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
    val is_video: Boolean
)