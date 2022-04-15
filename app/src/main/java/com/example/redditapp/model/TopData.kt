package com.example.redditapp.model

import com.google.gson.annotations.SerializedName

data class TopData(
    @SerializedName("data")
    val childrenData: ChildrenData,

    @SerializedName("after")
    val after: String?
)
