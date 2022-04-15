package com.example.redditapp.model

import com.google.gson.annotations.SerializedName

data class PostModelData(
    @SerializedName("data")
    val postModel: PostModel
)
